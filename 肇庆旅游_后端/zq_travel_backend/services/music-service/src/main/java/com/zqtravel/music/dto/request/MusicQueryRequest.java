package com.zqtravel.music.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 音乐查询请求参数
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@Schema(description = "音乐查询请求参数")
public class MusicQueryRequest {

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "分类类型: relax/nature/meditation/sleep")
    private String categoryType;

    @Schema(description = "音乐名称（模糊查询）")
    private String name;

    @Schema(description = "艺术家（模糊查询）")
    private String artist;

    @Schema(description = "标签")
    private String tag;

    @Schema(description = "状态: 0-下架, 1-上架")
    private Integer status;

    @Schema(description = "排序字段: play_count, favorite_count, created_at")
    private String sortBy = "created_at";

    @Schema(description = "排序方向: asc, desc")
    private String sortOrder = "desc";
}