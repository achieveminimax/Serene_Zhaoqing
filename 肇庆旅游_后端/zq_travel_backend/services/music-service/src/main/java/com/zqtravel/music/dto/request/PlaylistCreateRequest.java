package com.zqtravel.music.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 播放列表创建请求
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@Schema(description = "播放列表创建请求")
public class PlaylistCreateRequest {

    @Schema(description = "列表名称", required = true)
    @NotBlank(message = "列表名称不能为空")
    private String name;

    @Schema(description = "描述")
    private String description;
}