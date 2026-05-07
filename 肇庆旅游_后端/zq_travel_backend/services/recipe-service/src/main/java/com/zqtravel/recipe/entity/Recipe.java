package com.zqtravel.recipe.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 食谱实体类
 * 对应数据库表: recipes
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@Data
@TableName(value = "recipes", autoResultMap = true)
public class Recipe {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 食谱名称
     */
    private String name;

    /**
     * 分类ID
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 封面图URL
     */
    @TableField("image_url")
    private String imageUrl;

    /**
     * 描述
     */
    private String description;

    /**
     * 食材列表 (JSON格式)
     */
    @TableField(value = "ingredients", typeHandler = JacksonTypeHandler.class)
    private List<Ingredient> ingredients;

    /**
     * 步骤列表 (JSON格式)
     */
    @TableField(value = "steps", typeHandler = JacksonTypeHandler.class)
    private List<Step> steps;

    /**
     * 卡路里
     */
    private Integer calories;

    /**
     * 烹饪时间(分钟)
     */
    @TableField("cook_time")
    private Integer cookTime;

    /**
     * 难度
     */
    private String difficulty;

    /**
     * 标签 (JSON格式)
     */
    @TableField(value = "tags", typeHandler = JacksonTypeHandler.class)
    private List<String> tags;

    /**
     * 浏览次数
     */
    @TableField("view_count")
    private Integer viewCount;

    /**
     * 收藏次数
     */
    @TableField("favorite_count")
    private Integer favoriteCount;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /**
     * 是否删除
     */
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;

    /**
     * 食材类
     */
    @Data
    public static class Ingredient {
        /**
         * 食材名称
         */
        private String name;
        
        /**
         * 数量
         */
        private String amount;
        
        /**
         * 单位
         */
        private String unit;
        
        /**
         * 备注
         */
        private String note;
    }

    /**
     * 步骤类
     */
    @Data
    public static class Step {
        /**
         * 步骤序号
         */
        private Integer order;
        
        /**
         * 步骤描述
         */
        private String description;
        
        /**
         * 步骤图片URL
         */
        private String imageUrl;
        
        /**
         * 预计时间(分钟)
         */
        private Integer time;
    }
}