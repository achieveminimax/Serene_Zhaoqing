package com.zqtravel.scenic.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 推荐内容响应DTO
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@Schema(description = "推荐内容响应DTO")
public class RecommendDTO {

    @Schema(description = "推荐ID", example = "1")
    private Long id;

    @Schema(description = "推荐类型：scenic-景点，music-音乐，recipe-食谱，article-文章", example = "scenic")
    private String type;

    @Schema(description = "标题", example = "热门景点推荐")
    private String title;

    @Schema(description = "副标题", example = "探索肇庆最美风景")
    private String subtitle;

    @Schema(description = "图片URL", example = "https://example.com/images/recommend1.jpg")
    private String imageUrl;

    @Schema(description = "跳转链接", example = "/pages/scenic-detail/scenic-detail?id=1")
    private String linkUrl;

    @Schema(description = "排序", example = "1")
    private Integer sortOrder;

    @Schema(description = "是否启用", example = "true")
    private Boolean enabled;
}