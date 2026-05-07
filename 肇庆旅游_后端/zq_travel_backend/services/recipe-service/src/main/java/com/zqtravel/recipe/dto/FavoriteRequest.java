package com.zqtravel.recipe.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 收藏请求参数
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@Data
@Schema(description = "收藏请求")
public class FavoriteRequest {

    @Schema(description = "操作类型: add-收藏, remove-取消收藏", 
            allowableValues = {"add", "remove"}, 
            example = "add")
    private String action;
}