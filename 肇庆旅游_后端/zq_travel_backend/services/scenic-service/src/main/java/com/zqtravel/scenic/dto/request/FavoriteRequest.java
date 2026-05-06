package com.zqtravel.scenic.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * 收藏请求DTO
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@Schema(description = "收藏请求")
public class FavoriteRequest {

    @Schema(description = "用户ID", required = true, example = "1")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @Schema(description = "操作类型：add-添加收藏，remove-取消收藏", required = true, example = "add")
    @NotNull(message = "操作类型不能为空")
    private String action;
}