package com.zqtravel.recipe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 烹饪记录请求参数
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@Data
@Schema(description = "烹饪记录请求")
public class CookingRecordRequest {

    @Schema(description = "备注", example = "第一次尝试，味道不错")
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String note;

    @Schema(description = "评分 (1-5)", example = "5")
    @Min(value = 1, message = "评分最小为1")
    @Max(value = 5, message = "评分最大为5")
    private Integer rating;
}