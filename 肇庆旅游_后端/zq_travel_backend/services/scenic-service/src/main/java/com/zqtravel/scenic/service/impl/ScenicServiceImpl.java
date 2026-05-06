package com.zqtravel.scenic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zqtravel.scenic.converter.ScenicConverter;
import com.zqtravel.scenic.dto.request.NearbyScenicRequest;
import com.zqtravel.scenic.dto.request.ScenicQueryRequest;
import com.zqtravel.scenic.dto.response.ScenicSpotDTO;
import com.zqtravel.scenic.dto.vo.ScenicDetailVO;
import com.zqtravel.scenic.entity.ScenicSpot;
import com.zqtravel.scenic.exception.ScenicNotFoundException;
import com.zqtravel.scenic.repository.ScenicSpotRepository;
import com.zqtravel.scenic.service.FavoriteService;
import com.zqtravel.scenic.service.ScenicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 景点服务实现类
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ScenicServiceImpl implements ScenicService {

    private final ScenicSpotRepository scenicSpotRepository;
    private final FavoriteService favoriteService;

    @Override
    public IPage<ScenicSpotDTO> getScenicList(ScenicQueryRequest request) {
        log.debug("查询景点列表，参数: {}", request);

        // 构建分页参数
        Page<ScenicSpot> page = new Page<>(request.getPage(), request.getSize());

        // 构建查询条件
        QueryWrapper<ScenicSpot> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", 0);

        if (request.getCategoryId() != null) {
            queryWrapper.eq("category_id", request.getCategoryId());
        }

        if (request.getStatus() != null) {
            queryWrapper.eq("status", request.getStatus());
        }

        if (request.getKeyword() != null && !request.getKeyword().isEmpty()) {
            queryWrapper.and(wrapper -> wrapper
                .like("name", request.getKeyword())
                .or()
                .like("description", request.getKeyword())
            );
        }

        // 排序处理
        if ("view_count".equals(request.getSortBy())) {
            queryWrapper.orderBy(true, "asc".equals(request.getSortDirection()), "view_count");
        } else if ("favorite_count".equals(request.getSortBy())) {
            queryWrapper.orderBy(true, "asc".equals(request.getSortDirection()), "favorite_count");
        } else {
            queryWrapper.orderBy(true, "asc".equals(request.getSortDirection()), "created_at");
        }

        // 执行查询
        IPage<ScenicSpot> scenicPage = scenicSpotRepository.selectPage(page, queryWrapper);

        // 转换为DTO
        return scenicPage.convert(ScenicConverter::toDTO);
    }

    @Override
    @Transactional
    public ScenicDetailVO getScenicDetail(Long id, Long userId) {
        log.debug("获取景点详情，景点ID: {}, 用户ID: {}", id, userId);

        // 查询景点信息
        ScenicSpot scenicSpot = scenicSpotRepository.selectById(id);
        if (scenicSpot == null || scenicSpot.getIsDeleted() == 1) {
            throw new ScenicNotFoundException(id);
        }

        // 增加浏览计数
        scenicSpotRepository.incrementViewCount(id);

        // 转换为详情视图对象
        ScenicDetailVO detailVO = ScenicConverter.toDetailVO(scenicSpot);

        // 设置是否收藏
        if (userId != null) {
            boolean isFavorite = favoriteService.isFavorite(userId, "scenic", id);
            detailVO.setIsFavorite(isFavorite);
        }

        return detailVO;
    }

    @Override
    public List<ScenicSpotDTO> getHotSpots(Integer limit) {
        log.debug("获取热门景点，限制数量: {}", limit);

        List<ScenicSpot> hotSpots = scenicSpotRepository.selectHotSpots(limit);
        return ScenicConverter.toDTOList(hotSpots);
    }

    @Override
    public List<ScenicSpotDTO> getNearbyScenic(NearbyScenicRequest request, Long userId) {
        log.debug("获取附近景点，参数: {}, 用户ID: {}", request, userId);

        List<ScenicSpot> nearbySpots = scenicSpotRepository.selectNearbySpots(
            request.getLat(),
            request.getLng(),
            request.getRadius(),
            request.getLimit()
        );

        List<ScenicSpotDTO> dtos = ScenicConverter.toDTOList(nearbySpots);

        // 设置是否收藏
        if (userId != null) {
            dtos.forEach(dto -> {
                boolean isFavorite = favoriteService.isFavorite(userId, "scenic", dto.getId());
                dto.setIsFavorite(isFavorite);
            });
        }

        return dtos;
    }

    @Override
    public List<ScenicSpotDTO> searchScenic(String keyword, Integer limit) {
        log.debug("搜索景点，关键词: {}, 限制数量: {}", keyword, limit);

        QueryWrapper<ScenicSpot> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", 0)
                   .eq("status", 1)
                   .and(wrapper -> wrapper
                       .like("name", keyword)
                       .or()
                       .like("description", keyword)
                       .or()
                       .like("description_secondary", keyword)
                   )
                   .last("LIMIT " + limit);

        List<ScenicSpot> scenicSpots = scenicSpotRepository.selectList(queryWrapper);
        return ScenicConverter.toDTOList(scenicSpots);
    }

    @Override
    public void incrementViewCount(Long id) {
        log.debug("增加景点浏览计数，景点ID: {}", id);
        scenicSpotRepository.incrementViewCount(id);
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            return false;
        }
        ScenicSpot scenicSpot = scenicSpotRepository.selectById(id);
        return scenicSpot != null && scenicSpot.getIsDeleted() == 0;
    }

    @Override
    public List<ScenicSpotDTO> getSpotsByCategoryId(Long categoryId, Integer limit) {
        log.debug("根据分类ID获取景点列表，分类ID: {}, 限制数量: {}", categoryId, limit);

        QueryWrapper<ScenicSpot> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", 0)
                   .eq("status", 1)
                   .eq("category_id", categoryId)
                   .orderByDesc("created_at")
                   .last(limit != null ? "LIMIT " + limit : "");

        List<ScenicSpot> scenicSpots = scenicSpotRepository.selectList(queryWrapper);
        return ScenicConverter.toDTOList(scenicSpots);
    }
}