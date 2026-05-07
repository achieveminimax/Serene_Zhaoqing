package com.zqtravel.recipe.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 烹饪记录实体类
 * 对应数据库表: cooking_records
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@Data
@TableName("cooking_records")
public class CookingRecord {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 食谱ID
     */
    @TableField("recipe_id")
    private Long recipeId;

    /**
     * 烹饪时间
     */
    @TableField("cooked_at")
    private LocalDateTime cookedAt;

    /**
     * 备注
     */
    private String note;

    /**
     * 评分 (1-5)
     */
    private Integer rating;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;
}