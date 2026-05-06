package com.zqtravel.music.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

/**
 * 添加歌曲到播放列表请求
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@Schema(description = "添加歌曲到播放列表请求")
public class AddTrackRequest {

    @Schema(description = "音乐ID", required = true)
    @NotNull(message = "音乐ID不能为空")
    private Long musicId;

    @Schema(description = "排序位置，默认为最后")
    private Integer sortOrder;
}