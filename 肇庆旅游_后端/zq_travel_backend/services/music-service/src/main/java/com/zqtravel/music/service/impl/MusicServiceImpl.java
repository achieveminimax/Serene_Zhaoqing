package com.zqtravel.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zqtravel.music.dto.MusicDTO;
import com.zqtravel.music.dto.PageResult;
import com.zqtravel.music.dto.request.MusicQueryRequest;
import com.zqtravel.music.entity.Music;
import com.zqtravel.music.entity.MusicCategory;
import com.zqtravel.music.repository.MusicCategoryRepository;
import com.zqtravel.music.repository.MusicRepository;
import com.zqtravel.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 音乐服务实现类
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

    private final MusicRepository musicRepository;
    private final MusicCategoryRepository musicCategoryRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public PageResult<MusicDTO> getMusics(MusicQueryRequest request, Integer page, Integer size) {
        Page<Music> pageParam = new Page<>(page, size);
        QueryWrapper<Music> queryWrapper = buildQueryWrapper(request);
        
        Page<Music> musicPage = musicRepository.selectPage(pageParam, queryWrapper);
        
        List<MusicDTO> records = musicPage.getRecords().stream()
                .map(music -> convertToDTO(music, null)) // 暂时不传userId
                .collect(Collectors.toList());
        
        return PageResult.of(page, size, musicPage.getTotal(), records);
    }

    @Override
    @Cacheable(value = "music", key = "#id + '_' + #userId")
    public MusicDTO getMusicById(Long id, Long userId) {
        Music music = musicRepository.selectById(id);
        if (music == null || music.getDeleted() == 1) {
            return null;
        }
        
        // 异步更新播放次数（通过消息队列）
        if (userId != null) {
            sendPlayRecordMessage(id, userId);
        }
        
        return convertToDTO(music, userId);
    }

    @Override
    public PageResult<MusicDTO> getRelaxMusics(Integer page, Integer size) {
        MusicQueryRequest request = new MusicQueryRequest();
        request.setCategoryType("relax");
        request.setStatus(1);
        request.setSortBy("play_count");
        request.setSortOrder("desc");
        
        return getMusics(request, page, size);
    }

    @Override
    public PageResult<MusicDTO> getNatureSounds(Integer page, Integer size, String sortBy, String sortOrder) {
        MusicQueryRequest request = new MusicQueryRequest();
        request.setCategoryType("nature");
        request.setStatus(1);
        request.setSortBy(sortBy != null ? sortBy : "play_count");
        request.setSortOrder(sortOrder != null ? sortOrder : "desc");
        
        return getMusics(request, page, size);
    }

    @Override
    public PageResult<MusicDTO> getMeditationMusics(Long categoryId, Integer page, Integer size) {
        MusicQueryRequest request = new MusicQueryRequest();
        request.setCategoryType("meditation");
        if (categoryId != null) {
            request.setCategoryId(categoryId);
        }
        request.setStatus(1);
        request.setSortBy("sort_order");
        request.setSortOrder("asc");
        
        return getMusics(request, page, size);
    }

    @Override
    public PageResult<MusicDTO> getRecommendMusics(Long userId, Integer page, Integer size) {
        // 简化版推荐算法：返回热门音乐
        MusicQueryRequest request = new MusicQueryRequest();
        request.setStatus(1);
        request.setSortBy("play_count");
        request.setSortOrder("desc");
        
        return getMusics(request, page, size);
    }

    @Override
    @Transactional
    public void recordPlayCount(Long musicId, Long userId) {
        // 使用Redis原子递增，避免频繁更新数据库
        String key = "music:play:count:" + musicId;
        Long count = redisTemplate.opsForValue().increment(key, 1);
        
        // 每10次播放同步到数据库
        if (count != null && count % 10 == 0) {
            updatePlayCountInDb(musicId, count);
        }
        
        // 记录用户播放历史（异步）
        if (userId != null) {
            sendUserPlayHistoryMessage(musicId, userId);
        }
    }

    @Override
    @Cacheable(value = "lyrics", key = "#musicId")
    public String getLyrics(Long musicId) {
        Music music = musicRepository.selectById(musicId);
        if (music == null || music.getDeleted() == 1) {
            return null;
        }
        return music.getLyrics();
    }

    /**
     * 构建查询条件
     */
    private QueryWrapper<Music> buildQueryWrapper(MusicQueryRequest request) {
        QueryWrapper<Music> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", 0);
        
        if (request.getCategoryId() != null) {
            queryWrapper.eq("category_id", request.getCategoryId());
        }
        
        if (StringUtils.hasText(request.getCategoryType())) {
            // 需要联表查询，这里简化处理：先查询分类ID
            QueryWrapper<MusicCategory> categoryQuery = new QueryWrapper<>();
            categoryQuery.eq("type", request.getCategoryType());
            List<MusicCategory> categories = musicCategoryRepository.selectList(categoryQuery);
            if (!categories.isEmpty()) {
                List<Long> categoryIds = categories.stream()
                        .map(MusicCategory::getId)
                        .collect(Collectors.toList());
                queryWrapper.in("category_id", categoryIds);
            }
        }
        
        if (StringUtils.hasText(request.getName())) {
            queryWrapper.like("name", request.getName());
        }
        
        if (StringUtils.hasText(request.getArtist())) {
            queryWrapper.like("artist", request.getArtist());
        }
        
        if (StringUtils.hasText(request.getTag())) {
            queryWrapper.like("tag", request.getTag());
        }
        
        if (request.getStatus() != null) {
            queryWrapper.eq("status", request.getStatus());
        } else {
            queryWrapper.eq("status", 1); // 默认只查询上架的音乐
        }
        
        // 排序
        if (StringUtils.hasText(request.getSortBy())) {
            String orderBy = request.getSortBy();
            if ("play_count".equals(orderBy) || "favorite_count".equals(orderBy) || "created_at".equals(orderBy)) {
                boolean isAsc = "asc".equalsIgnoreCase(request.getSortOrder());
                queryWrapper.orderBy(true, isAsc, orderBy);
            }
        } else {
            queryWrapper.orderByDesc("created_at");
        }
        
        return queryWrapper;
    }

    /**
     * 将实体转换为DTO
     */
    private MusicDTO convertToDTO(Music music, Long userId) {
        if (music == null) {
            return null;
        }
        
        MusicDTO dto = new MusicDTO();
        dto.setId(music.getId());
        dto.setName(music.getName());
        dto.setArtist(music.getArtist());
        dto.setEmoji(music.getEmoji());
        dto.setTag(music.getTag());
        dto.setDuration(music.getDuration());
        dto.setImageUrl(music.getImageUrl());
        dto.setAudioUrl(music.getAudioUrl());
        dto.setCategoryId(music.getCategoryId());
        dto.setLyrics(music.getLyrics());
        dto.setPlayCount(music.getPlayCount());
        dto.setFavoriteCount(music.getFavoriteCount());
        dto.setStatus(music.getStatus());
        dto.setCreatedAt(music.getCreatedAt());
        dto.setUpdatedAt(music.getUpdatedAt());
        
        // 查询分类名称
        if (music.getCategoryId() != null) {
            MusicCategory category = musicCategoryRepository.selectById(music.getCategoryId());
            if (category != null) {
                dto.setCategoryName(category.getName());
            }
        }
        
        // 判断是否收藏（需要调用收藏服务，这里暂时返回false）
        dto.setIsFavorited(false);
        
        return dto;
    }

    /**
     * 发送播放记录消息到RabbitMQ
     */
    private void sendPlayRecordMessage(Long musicId, Long userId) {
        try {
            PlayRecordMessage message = new PlayRecordMessage();
            message.setMusicId(musicId);
            message.setUserId(userId);
            message.setPlayTime(LocalDateTime.now());
            
            rabbitTemplate.convertAndSend("music.exchange", "music.play", message);
            log.debug("发送播放记录消息: {}", message);
        } catch (Exception e) {
            log.error("发送播放记录消息失败", e);
        }
    }

    /**
     * 发送用户播放历史消息
     */
    private void sendUserPlayHistoryMessage(Long musicId, Long userId) {
        // 实现略
    }

    /**
     * 更新数据库中的播放次数
     */
    private void updatePlayCountInDb(Long musicId, Long count) {
        Music music = new Music();
        music.setId(musicId);
        music.setPlayCount(count.intValue());
        music.setUpdatedAt(LocalDateTime.now());
        musicRepository.updateById(music);
    }

    /**
     * 播放记录消息
     */
    private static class PlayRecordMessage {
        private Long musicId;
        private Long userId;
        private LocalDateTime playTime;
        
        public Long getMusicId() {
            return musicId;
        }
        
        public void setMusicId(Long musicId) {
            this.musicId = musicId;
        }
        
        public Long getUserId() {
            return userId;
        }
        
        public void setUserId(Long userId) {
            this.userId = userId;
        }
        
        public LocalDateTime getPlayTime() {
            return playTime;
        }
        
        public void setPlayTime(LocalDateTime playTime) {
            this.playTime = playTime;
        }
    }
}