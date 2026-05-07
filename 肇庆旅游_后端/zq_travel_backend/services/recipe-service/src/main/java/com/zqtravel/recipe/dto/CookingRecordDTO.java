package com.zqtravel.recipe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 烹饪记录传输对象
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@Data
@Schema(description = "烹饪记录信息")
public class CookingRecordDTO {

    @Schema(description = "记录ID", example = "1")
    private Long id;

    @Schema(description = "用户ID", example = "1001")
    private Long userId;

    @Schema(description = "食谱ID", example = "1")
    private Long recipeId;

    @Schema(description = "食谱名称", example = "肇庆裹蒸粽")
    private String recipeName;

    @Schema(description = "食谱图片", example = "https://example.com/recipe1.jpg")
    private String recipeImage;

    @Schema(description = "烹饪时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cookedAt;

    @Schema(description = "备注", example = "第一次尝试，味道不错")
    private String note;

    @Schema(description = "评分 (1-5)", example = "5")
    private Integer rating;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}