package com.zqtravel.shop.service.impl;

import com.zqtravel.shop.dto.response.CategoryDTO;
import com.zqtravel.shop.entity.ProductCategory;
import com.zqtravel.shop.mapper.CategoryMapper;
import com.zqtravel.shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Override
    @Cacheable(value = "categories", key = "'all'")
    public List<CategoryDTO> listAllCategories() {
        List<ProductCategory> categories = categoryMapper.selectAllCategories();
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "categoryTree", key = "'tree'")
    public List<CategoryDTO> getCategoryTree() {
        List<ProductCategory> categories = categoryMapper.selectCategoryTree();
        return buildCategoryTree(categories, 0L);
    }

    @Override
    @Cacheable(value = "category", key = "#id")
    public CategoryDTO getCategoryById(Long id) {
        ProductCategory category = categoryMapper.selectById(id);
        return category != null ? convertToDTO(category) : null;
    }

    @Override
    public List<CategoryDTO> getCategoriesByParentId(Long parentId) {
        List<ProductCategory> categories = categoryMapper.selectByParentId(parentId);
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CategoryDTO convertToDTO(ProductCategory category) {
        CategoryDTO dto = new CategoryDTO();
        BeanUtils.copyProperties(category, dto);
        return dto;
    }

    private List<CategoryDTO> buildCategoryTree(List<ProductCategory> categories, Long parentId) {
        return categories.stream()
                .filter(category -> parentId.equals(category.getParentId()))
                .map(category -> {
                    CategoryDTO dto = convertToDTO(category);
                    dto.setChildren(buildCategoryTree(categories, category.getId()));
                    return dto;
                })
                .collect(Collectors.toList());
    }
}