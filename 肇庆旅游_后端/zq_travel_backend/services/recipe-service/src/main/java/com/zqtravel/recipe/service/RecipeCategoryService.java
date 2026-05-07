package com.zqtravel.recipe.service;

import com.zqtravel.recipe.dto.RecipeCategoryDTO;

import java.util.List;

/**
 * 食谱分类服务接口
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
public interface RecipeCategoryService {

    /**
     * 获取所有食谱分类（按排序）
     * 
     * @return 分类列表
     */
    List<RecipeCategoryDTO> getAllCategories();

    /**
     * 根据ID获取分类
     * 
     * @param id 分类ID
     * @return 分类信息
     */
    RecipeCategoryDTO getCategoryById(Long id);

    /**
     * 根据名称获取分类
     * 
     * @param name 分类名称
     * @return 分类信息
     */
    RecipeCategoryDTO getCategoryByName(String name);
}