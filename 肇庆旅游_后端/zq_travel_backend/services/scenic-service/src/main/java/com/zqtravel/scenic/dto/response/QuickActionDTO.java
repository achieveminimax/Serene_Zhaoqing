package com.zqtravel.scenic.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 快捷操作入口响应DTO
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@Schema(description = "快捷操作入口响应DTO")
public class QuickActionDTO {

    @Schema(description = "操作ID", example = "1")
    private Long id;

    @Schema(description = "操作名称", example = "景点搜索")
    private String name;

    @Schema(description = "图标URL", example = "https://example.com/icons/search.png")
    private String iconUrl;

    @Schema(description = "跳转链接", example = "/pages/search/search")
    private String linkUrl;

    @Schema(description = "背景颜色", example = "#4CAF50")
    private String backgroundColor;

    @Schema(description = "排序", example = "1")
    private Integer sortOrder;

    @Schema(description = "是否启用", example = "true")
    private Boolean enabled;
}