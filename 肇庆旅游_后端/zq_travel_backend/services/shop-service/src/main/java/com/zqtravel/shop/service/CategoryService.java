package com.zqtravel.shop.service;

import com.zqtravel.shop.dto.response.CategoryDTO;

import java.util.List;

public interface CategoryService {

    /**
     * 获取所有分类
     */
    List<CategoryDTO> listAllCategories();

    /**
     * 获取分类树形结构
     */
    List<CategoryDTO> getCategoryTree();

    /**
     * 根据ID获取分类
     */
    CategoryDTO getCategoryById(Long id);

    /**
     * 根据父分类ID获取子分类
     */
    List<CategoryDTO> getCategoriesByParentId(Long parentId);
}