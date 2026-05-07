package com.zqtravel.recipe.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zqtravel.recipe.dto.RecipeDTO;
import com.zqtravel.recipe.dto.FavoriteRequest;

import java.util.List;

/**
 * 食谱服务接口
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
public interface RecipeService {

    /**
     * 分页查询食谱列表
     * 
     * @param page 页码
     * @param size 每页大小
     * @param categoryId 分类ID（可选）
     * @param difficulty 难度（可选）
     * @param userId 用户ID（用于判断是否收藏）
     * @return 食谱分页列表
     */
    IPage<RecipeDTO> getRecipes(Integer page, Integer size, Long categoryId, String difficulty, Long userId);

    /**
     * 获取食谱详情
     * 
     * @param id 食谱ID
     * @param userId 用户ID（用于判断是否收藏）
     * @return 食谱详情
     */
    RecipeDTO getRecipeById(Long id, Long userId);

    /**
     * 获取热门食谱
     * 
     * @param limit 限制数量
     * @param userId 用户ID（用于判断是否收藏）
     * @return 热门食谱列表
     */
    List<RecipeDTO> getPopularRecipes(int limit, Long userId);

    /**
     * 收藏/取消收藏食谱
     * 
     * @param recipeId 食谱ID
     * @param userId 用户ID
     * @param request 收藏请求
     * @return 操作结果
     */
    boolean favoriteRecipe(Long recipeId, Long userId, FavoriteRequest request);

    /**
     * 获取用户收藏的食谱
     * 
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 收藏食谱分页列表
     */
    IPage<RecipeDTO> getFavoriteRecipes(Long userId, Integer page, Integer size);

    /**
     * 检查用户是否收藏了食谱
     * 
     * @param recipeId 食谱ID
     * @param userId 用户ID
     * @return 是否收藏
     */
    boolean isFavorite(Long recipeId, Long userId);

    /**
     * 增加食谱浏览次数
     * 
     * @param id 食谱ID
     */
    void incrementViewCount(Long id);
}