package com.zqtravel.recipe.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zqtravel.recipe.entity.RecipeCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 食谱分类数据访问接口
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@Mapper
public interface RecipeCategoryRepository extends BaseMapper<RecipeCategory> {

    /**
     * 按排序查询所有分类
     * 
     * @return 分类列表
     */
    List<RecipeCategory> selectAllOrderBySort();
}