package com.zqtravel.scenic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zqtravel.scenic.converter.FavoriteConverter;
import com.zqtravel.scenic.dto.response.FavoriteDTO;
import com.zqtravel.scenic.entity.UserFavorite;
import com.zqtravel.scenic.exception.FavoriteException;
import com.zqtravel.scenic.repository.ScenicSpotRepository;
import com.zqtravel.scenic.repository.UserFavoriteRepository;
import com.zqtravel.scenic.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 收藏服务实现类
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final UserFavoriteRepository userFavoriteRepository;
    private final ScenicSpotRepository scenicSpotRepository;

    @Override
    @Transactional
    public Long addFavorite(Long userId, String targetType, Long targetId) {
        log.debug("添加收藏，用户ID: {}, 目标类型: {}, 目标ID: {}", userId, targetType, targetId);

        // 检查是否已收藏
        UserFavorite existingFavorite = userFavoriteRepository.selectUserFavorite(userId, targetType, targetId);
        if (existingFavorite != null) {
            throw FavoriteException.alreadyFavorited(userId, targetId);
        }

        // 验证目标是否存在（这里以景点为例）
        if ("scenic".equals(targetType)) {
            boolean exists = scenicSpotRepository.existsById(targetId);
            if (!exists) {
                throw new FavoriteException("景点不存在，ID: " + targetId);
            }
        }

        // 创建收藏记录
        UserFavorite favorite = new UserFavorite();
        favorite.setUserId(userId);
        favorite.setTargetType(targetType);
        favorite.setTargetId(targetId);
        favorite.setCreatedAt(new Date());

        int result = userFavoriteRepository.insert(favorite);
        if (result <= 0) {
            throw new FavoriteException("添加收藏失败");
        }

        // 更新景点收藏计数
        if ("scenic".equals(targetType)) {
            scenicSpotRepository.incrementFavoriteCount(targetId);
        }

        log.info("收藏添加成功，收藏ID: {}, 用户ID: {}, 目标类型: {}, 目标ID: {}", 
                favorite.getId(), userId, targetType, targetId);
        
        return favorite.getId();
    }

    @Override
    @Transactional
    public boolean removeFavorite(Long userId, String targetType, Long targetId) {
        log.debug("取消收藏，用户ID: {}, 目标类型: {}, 目标ID: {}", userId, targetType, targetId);

        // 检查是否已收藏
        UserFavorite existingFavorite = userFavoriteRepository.selectUserFavorite(userId, targetType, targetId);
        if (existingFavorite == null) {
            throw FavoriteException.notFavorited(userId, targetId);
        }

        // 删除收藏记录
        QueryWrapper<UserFavorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .eq("target_type", targetType)
                   .eq("target_id", targetId);

        int result = userFavoriteRepository.delete(queryWrapper);
        if (result <= 0) {
            throw new FavoriteException("取消收藏失败");
        }

        // 更新景点收藏计数
        if ("scenic".equals(targetType)) {
            scenicSpotRepository.decrementFavoriteCount(targetId);
        }

        log.info("收藏取消成功，用户ID: {}, 目标类型: {}, 目标ID: {}", userId, targetType, targetId);
        return true;
    }

    @Override
    public List<FavoriteDTO> getUserFavorites(Long userId, String targetType) {
        log.debug("获取用户收藏列表，用户ID: {}, 目标类型: {}", userId, targetType);

        List<UserFavorite> favorites;
        if (targetType == null || targetType.isEmpty()) {
            // 查询所有类型的收藏
            QueryWrapper<UserFavorite> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId)
                       .orderByDesc("created_at");
            favorites = userFavoriteRepository.selectList(queryWrapper);
        } else {
            // 查询指定类型的收藏
            favorites = userFavoriteRepository.selectUserFavorites(userId, targetType);
        }

        return FavoriteConverter.toDTOList(favorites);
    }

    @Override
    public boolean isFavorite(Long userId, String targetType, Long targetId) {
        log.debug("检查用户是否已收藏，用户ID: {}, 目标类型: {}, 目标ID: {}", userId, targetType, targetId);

        UserFavorite favorite = userFavoriteRepository.selectUserFavorite(userId, targetType, targetId);
        return favorite != null;
    }

    @Override
    public Integer getUserFavoriteCount(Long userId, String targetType) {
        log.debug("获取用户收藏数量，用户ID: {}, 目标类型: {}", userId, targetType);

        if (targetType == null || targetType.isEmpty()) {
            QueryWrapper<UserFavorite> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId);
            Long count = userFavoriteRepository.selectCount(queryWrapper);
            return count != null ? count.intValue() : 0;
        } else {
            Integer count = userFavoriteRepository.countUserFavorites(userId, targetType);
            return count != null ? count : 0;
        }
    }

    @Override
    @Transactional
    public boolean toggleFavorite(Long userId, String targetType, Long targetId) {
        log.debug("切换收藏状态，用户ID: {}, 目标类型: {}, 目标ID: {}", userId, targetType, targetId);

        boolean isFavorite = isFavorite(userId, targetType, targetId);
        
        if (isFavorite) {
            // 如果已收藏，则取消收藏
            removeFavorite(userId, targetType, targetId);
            return false;
        } else {
            // 如果未收藏，则添加收藏
            addFavorite(userId, targetType, targetId);
            return true;
        }
    }
}