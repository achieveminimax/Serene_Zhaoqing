package com.zqtravel.scenic.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 轮播图响应DTO
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@Schema(description = "轮播图响应DTO")
public class BannerDTO {

    @Schema(description = "轮播图ID", example = "1")
    private Long id;

    @Schema(description = "标题", example = "七星岩美景")
    private String title;

    @Schema(description = "图片URL", example = "https://example.com/images/banner1.jpg")
    private String imageUrl;

    @Schema(description = "跳转链接", example = "/pages/scenic-detail/scenic-detail?id=1")
    private String linkUrl;

    @Schema(description = "排序", example = "1")
    private Integer sortOrder;

    @Schema(description = "是否启用", example = "true")
    private Boolean enabled;
}