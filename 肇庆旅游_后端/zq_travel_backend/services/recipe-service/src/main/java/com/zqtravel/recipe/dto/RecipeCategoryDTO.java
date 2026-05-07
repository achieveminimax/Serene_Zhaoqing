package com.zqtravel.recipe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 食谱分类传输对象
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@Data
@Schema(description = "食谱分类信息")
public class RecipeCategoryDTO {

    @Schema(description = "分类ID", example = "1")
    private Long id;

    @Schema(description = "分类名称", example = "中式菜肴")
    private String name;

    @Schema(description = "图标URL", example = "https://example.com/icons/chinese.png")
    private String icon;

    @Schema(description = "排序", example = "1")
    private Integer sortOrder;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}