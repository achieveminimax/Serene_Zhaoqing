package com.zqtravel.scenic.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 景点分类响应DTO
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@Schema(description = "景点分类响应DTO")
public class ScenicCategoryDTO {

    @Schema(description = "分类ID", example = "1")
    private Long id;

    @Schema(description = "分类名称", example = "自然风光")
    private String name;

    @Schema(description = "图标URL", example = "https://example.com/icons/nature.png")
    private String icon;

    @Schema(description = "排序", example = "1")
    private Integer sortOrder;

    @Schema(description = "景点数量", example = "10")
    private Integer spotCount;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
}