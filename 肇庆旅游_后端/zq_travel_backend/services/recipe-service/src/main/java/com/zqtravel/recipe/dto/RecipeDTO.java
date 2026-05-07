package com.zqtravel.recipe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zqtravel.recipe.entity.Recipe;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 食谱信息传输对象
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@Data
@Schema(description = "食谱信息")
public class RecipeDTO {

    @Schema(description = "食谱ID", example = "1")
    private Long id;

    @Schema(description = "食谱名称", example = "肇庆裹蒸粽")
    private String name;

    @Schema(description = "分类ID", example = "1")
    private Long categoryId;

    @Schema(description = "分类名称", example = "中式菜肴")
    private String categoryName;

    @Schema(description = "封面图URL", example = "https://example.com/recipe1.jpg")
    private String imageUrl;

    @Schema(description = "描述", example = "肇庆传统美食，香气扑鼻，口感丰富")
    private String description;

    @Schema(description = "食材列表")
    private List<IngredientDTO> ingredients;

    @Schema(description = "步骤列表")
    private List<StepDTO> steps;

    @Schema(description = "卡路里", example = "350")
    private Integer calories;

    @Schema(description = "烹饪时间(分钟)", example = "60")
    private Integer cookTime;

    @Schema(description = "难度", example = "中等")
    private String difficulty;

    @Schema(description = "标签")
    private List<String> tags;

    @Schema(description = "浏览次数", example = "100")
    private Integer viewCount;

    @Schema(description = "收藏次数", example = "50")
    private Integer favoriteCount;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "是否已收藏", example = "true")
    private Boolean isFavorite;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    /**
     * 食材DTO
     */
    @Data
    @Schema(description = "食材信息")
    public static class IngredientDTO {
        @Schema(description = "食材名称", example = "糯米")
        private String name;
        
        @Schema(description = "数量", example = "500")
        private String amount;
        
        @Schema(description = "单位", example = "克")
        private String unit;
        
        @Schema(description = "备注", example = "提前浸泡2小时")
        private String note;
    }

    /**
     * 步骤DTO
     */
    @Data
    @Schema(description = "步骤信息")
    public static class StepDTO {
        @Schema(description = "步骤序号", example = "1")
        private Integer order;
        
        @Schema(description = "步骤描述", example = "将糯米洗净，浸泡2小时")
        private String description;
        
        @Schema(description = "步骤图片URL", example = "https://example.com/step1.jpg")
        private String imageUrl;
        
        @Schema(description = "预计时间(分钟)", example = "10")
        private Integer time;
    }
}