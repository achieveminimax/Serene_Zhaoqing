package com.zqtravel.scenic.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Min;

/**
 * 景点查询请求DTO
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@Schema(description = "景点查询请求")
public class ScenicQueryRequest {

    @Schema(description = "页码，从1开始", example = "1")
    @Min(value = 1, message = "页码必须大于0")
    private Integer page = 1;

    @Schema(description = "每页大小", example = "20")
    @Min(value = 1, message = "每页大小必须大于0")
    private Integer size = 20;

    @Schema(description = "分类ID", example = "1")
    private Long categoryId;

    @Schema(description = "状态：0-下架，1-上架", example = "1")
    private Integer status;

    @Schema(description = "关键词搜索", example = "七星岩")
    private String keyword;

    @Schema(description = "排序字段：view_count-浏览量，favorite_count-收藏量，created_at-创建时间", example = "created_at")
    private String sortBy = "created_at";

    @Schema(description = "排序方向：asc-升序，desc-降序", example = "desc")
    private String sortDirection = "desc";
}