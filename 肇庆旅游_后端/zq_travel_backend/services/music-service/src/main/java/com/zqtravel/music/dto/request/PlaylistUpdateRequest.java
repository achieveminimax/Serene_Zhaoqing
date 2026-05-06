package com.zqtravel.music.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 播放列表更新请求
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@Schema(description = "播放列表更新请求")
public class PlaylistUpdateRequest {

    @Schema(description = "列表名称")
    private String name;

    @Schema(description = "描述")
    private String description;
}