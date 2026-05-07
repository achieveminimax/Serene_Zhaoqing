package com.zqtravel.recipe.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 食谱分类实体类
 * 对应数据库表: recipe_categories
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@Data
@TableName("recipe_categories")
public class RecipeCategory {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 图标URL
     */
    private String icon;

    /**
     * 排序
     */
    @TableField("sort_order")
    private Integer sortOrder;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}