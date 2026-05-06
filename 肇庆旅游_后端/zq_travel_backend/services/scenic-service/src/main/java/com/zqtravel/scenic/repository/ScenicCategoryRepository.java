package com.zqtravel.scenic.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zqtravel.scenic.entity.ScenicCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 景点分类数据访问接口
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Mapper
public interface ScenicCategoryRepository extends BaseMapper<ScenicCategory> {

    /**
     * 查询所有分类（按排序字段排序）
     *
     * @return 分类列表
     */
    @Select("SELECT * FROM scenic_categories ORDER BY sort_order ASC, created_at DESC")
    List<ScenicCategory> selectAllCategories();
}