package com.zqtravel.music.service;

import com.zqtravel.music.dto.MusicCategoryDTO;
import com.zqtravel.music.dto.PageResult;

import java.util.List;

/**
 * 音乐分类服务接口
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
public interface MusicCategoryService {

    /**
     * 获取所有音乐分类（分页）
     * 
     * @param page 页码
     * @param size 每页大小
     * @return 分页结果
     */
    PageResult<MusicCategoryDTO> getCategories(Integer page, Integer size);

    /**
     * 获取所有音乐分类（不分页）
     * 
     * @return 分类列表
     */
    List<MusicCategoryDTO> getAllCategories();

    /**
     * 根据类型获取分类
     * 
     * @param type 分类类型
     * @return 分类列表
     */
    List<MusicCategoryDTO> getCategoriesByType(String type);

    /**
     * 根据ID获取分类
     * 
     * @param id 分类ID
     * @return 分类信息
     */
    MusicCategoryDTO getCategoryById(Long id);
}