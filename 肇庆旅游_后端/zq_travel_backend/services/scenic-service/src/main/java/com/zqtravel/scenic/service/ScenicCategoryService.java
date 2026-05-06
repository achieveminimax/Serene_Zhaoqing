package com.zqtravel.scenic.service;

import com.zqtravel.scenic.dto.response.ScenicCategoryDTO;

import java.util.List;

/**
 * 景点分类服务接口
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
public interface ScenicCategoryService {

    /**
     * 获取所有景点分类
     *
     * @return 分类列表
     */
    List<ScenicCategoryDTO> getAllCategories();

    /**
     * 根据ID获取分类
     *
     * @param id 分类ID
     * @return 分类信息
     */
    ScenicCategoryDTO getCategoryById(Long id);

    /**
     * 检查分类是否存在
     *
     * @param id 分类ID
     * @return 是否存在
     */
    boolean existsById(Long id);

    /**
     * 获取分类下的景点数量
     *
     * @param categoryId 分类ID
     * @return 景点数量
     */
    Integer getSpotCountByCategoryId(Long categoryId);
}