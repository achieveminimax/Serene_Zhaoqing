package com.zqtravel.recipe.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zqtravel.recipe.entity.Recipe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 食谱数据访问接口
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@Mapper
public interface RecipeRepository extends BaseMapper<Recipe> {

    /**
     * 分页查询食谱列表
     * 
     * @param page 分页参数
     * @param categoryId 分类ID（可选）
     * @param difficulty 难度（可选）
     * @param status 状态（可选）
     * @return 食谱分页列表
     */
    IPage<Recipe> selectRecipePage(Page<Recipe> page,
                                   @Param("categoryId") Long categoryId,
                                   @Param("difficulty") String difficulty,
                                   @Param("status") Integer status);

    /**
     * 根据分类ID查询食谱列表
     * 
     * @param categoryId 分类ID
     * @return 食谱列表
     */
    List<Recipe> selectByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * 查询热门食谱（按浏览次数和收藏次数）
     * 
     * @param limit 限制数量
     * @return 热门食谱列表
     */
    List<Recipe> selectPopularRecipes(@Param("limit") int limit);

    /**
     * 增加食谱浏览次数
     * 
     * @param id 食谱ID
     * @return 影响行数
     */
    int incrementViewCount(@Param("id") Long id);

    /**
     * 增加食谱收藏次数
     * 
     * @param id 食谱ID
     * @param increment 增量（正数增加，负数减少）
     * @return 影响行数
     */
    int updateFavoriteCount(@Param("id") Long id, @Param("increment") int increment);
}