package com.zqtravel.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zqtravel.music.dto.MusicCategoryDTO;
import com.zqtravel.music.dto.PageResult;
import com.zqtravel.music.entity.MusicCategory;
import com.zqtravel.music.repository.MusicCategoryRepository;
import com.zqtravel.music.service.MusicCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 音乐分类服务实现类
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MusicCategoryServiceImpl implements MusicCategoryService {

    private final MusicCategoryRepository musicCategoryRepository;

    @Override
    public PageResult<MusicCategoryDTO> getCategories(Integer page, Integer size) {
        Page<MusicCategory> pageParam = new Page<>(page, size);
        QueryWrapper<MusicCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort_order");
        
        Page<MusicCategory> categoryPage = musicCategoryRepository.selectPage(pageParam, queryWrapper);
        
        List<MusicCategoryDTO> records = categoryPage.getRecords().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        
        return PageResult.of(page, size, categoryPage.getTotal(), records);
    }

    @Override
    @Cacheable(value = "musicCategories", key = "'all'")
    public List<MusicCategoryDTO> getAllCategories() {
        QueryWrapper<MusicCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort_order");
        
        List<MusicCategory> categories = musicCategoryRepository.selectList(queryWrapper);
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "musicCategories", key = "#type")
    public List<MusicCategoryDTO> getCategoriesByType(String type) {
        QueryWrapper<MusicCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type)
                   .orderByAsc("sort_order");
        
        List<MusicCategory> categories = musicCategoryRepository.selectList(queryWrapper);
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "musicCategory", key = "#id")
    public MusicCategoryDTO getCategoryById(Long id) {
        MusicCategory category = musicCategoryRepository.selectById(id);
        if (category == null) {
            return null;
        }
        return convertToDTO(category);
    }

    /**
     * 将实体转换为DTO
     */
    private MusicCategoryDTO convertToDTO(MusicCategory category) {
        if (category == null) {
            return null;
        }
        
        MusicCategoryDTO dto = new MusicCategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setType(category.getType());
        dto.setIcon(category.getIcon());
        dto.setSortOrder(category.getSortOrder());
        dto.setCreatedAt(category.getCreatedAt());
        return dto;
    }
}