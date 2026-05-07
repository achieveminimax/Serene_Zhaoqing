package com.zqtravel.recipe.service.impl;

import com.zqtravel.recipe.dto.RecipeCategoryDTO;
import com.zqtravel.recipe.entity.RecipeCategory;
import com.zqtravel.recipe.repository.RecipeCategoryRepository;
import com.zqtravel.recipe.service.RecipeCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 食谱分类服务实现类
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeCategoryServiceImpl implements RecipeCategoryService {

    private final RecipeCategoryRepository recipeCategoryRepository;

    @Override
    public List<RecipeCategoryDTO> getAllCategories() {
        List<RecipeCategory> categories = recipeCategoryRepository.selectAllOrderBySort();
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RecipeCategoryDTO getCategoryById(Long id) {
        RecipeCategory category = recipeCategoryRepository.selectById(id);
        if (category == null) {
            return null;
        }
        return convertToDTO(category);
    }

    @Override
    public RecipeCategoryDTO getCategoryByName(String name) {
        // 这里需要实现按名称查询，暂时简化
        List<RecipeCategory> categories = recipeCategoryRepository.selectList(null);
        return categories.stream()
                .filter(category -> name.equals(category.getName()))
                .findFirst()
                .map(this::convertToDTO)
                .orElse(null);
    }

    /**
     * 将RecipeCategory实体转换为RecipeCategoryDTO
     */
    private RecipeCategoryDTO convertToDTO(RecipeCategory category) {
        RecipeCategoryDTO dto = new RecipeCategoryDTO();
        BeanUtils.copyProperties(category, dto);
        return dto;
    }
}