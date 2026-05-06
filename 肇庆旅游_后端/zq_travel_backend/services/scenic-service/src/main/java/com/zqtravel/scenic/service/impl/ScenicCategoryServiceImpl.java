package com.zqtravel.scenic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zqtravel.scenic.converter.ScenicCategoryConverter;
import com.zqtravel.scenic.dto.response.ScenicCategoryDTO;
import com.zqtravel.scenic.entity.ScenicCategory;
import com.zqtravel.scenic.repository.ScenicCategoryRepository;
import com.zqtravel.scenic.repository.ScenicSpotRepository;
import com.zqtravel.scenic.service.ScenicCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 景点分类服务实现类
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ScenicCategoryServiceImpl implements ScenicCategoryService {

    private final ScenicCategoryRepository scenicCategoryRepository;
    private final ScenicSpotRepository scenicSpotRepository;

    @Override
    public List<ScenicCategoryDTO> getAllCategories() {
        log.debug("获取所有景点分类");

        List<ScenicCategory> categories = scenicCategoryRepository.selectAllCategories();
        List<ScenicCategoryDTO> dtos = ScenicCategoryConverter.toDTOList(categories);

        // 设置每个分类的景点数量
        dtos.forEach(dto -> {
            Integer spotCount = getSpotCountByCategoryId(dto.getId());
            dto.setSpotCount(spotCount);
        });

        return dtos;
    }

    @Override
    public ScenicCategoryDTO getCategoryById(Long id) {
        log.debug("根据ID获取分类，分类ID: {}", id);

        ScenicCategory category = scenicCategoryRepository.selectById(id);
        if (category == null) {
            return null;
        }

        ScenicCategoryDTO dto = ScenicCategoryConverter.toDTO(category);
        if (dto != null) {
            Integer spotCount = getSpotCountByCategoryId(dto.getId());
            dto.setSpotCount(spotCount);
        }

        return dto;
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            return false;
        }
        ScenicCategory category = scenicCategoryRepository.selectById(id);
        return category != null;
    }

    @Override
    public Integer getSpotCountByCategoryId(Long categoryId) {
        log.debug("获取分类下的景点数量，分类ID: {}", categoryId);

        QueryWrapper<ScenicCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", categoryId);
        ScenicCategory category = scenicCategoryRepository.selectOne(queryWrapper);
        
        if (category == null) {
            return 0;
        }

        // 查询该分类下的景点数量
        QueryWrapper<com.zqtravel.scenic.entity.ScenicSpot> spotQueryWrapper = new QueryWrapper<>();
        spotQueryWrapper.eq("category_id", categoryId)
                       .eq("is_deleted", 0)
                       .eq("status", 1);
        
        Long count = scenicSpotRepository.selectCount(spotQueryWrapper);
        return count != null ? count.intValue() : 0;
    }
}