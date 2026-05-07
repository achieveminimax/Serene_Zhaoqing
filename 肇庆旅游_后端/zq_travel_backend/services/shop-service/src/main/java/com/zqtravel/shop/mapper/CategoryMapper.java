package com.zqtravel.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zqtravel.shop.entity.ProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper extends BaseMapper<ProductCategory> {

    /**
     * 查询所有分类（树形结构）
     */
    @Select("SELECT * FROM product_categories ORDER BY parent_id, sort_order, created_at")
    List<ProductCategory> selectAllCategories();

    /**
     * 根据父分类ID查询子分类
     */
    @Select("SELECT * FROM product_categories WHERE parent_id = #{parentId} ORDER BY sort_order, created_at")
    List<ProductCategory> selectByParentId(@Param("parentId") Long parentId);

    /**
     * 查询分类树（递归查询）
     */
    @Select("WITH RECURSIVE category_tree AS (" +
            "  SELECT id, name, parent_id, icon, sort_order, created_at, 1 AS level " +
            "  FROM product_categories WHERE parent_id = 0 " +
            "  UNION ALL " +
            "  SELECT c.id, c.name, c.parent_id, c.icon, c.sort_order, c.created_at, ct.level + 1 " +
            "  FROM product_categories c " +
            "  INNER JOIN category_tree ct ON c.parent_id = ct.id" +
            ") SELECT * FROM category_tree ORDER BY level, sort_order")
    List<ProductCategory> selectCategoryTree();
}