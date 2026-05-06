package com.zqtravel.music.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 播放列表数据传输对象
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@Schema(description = "播放列表信息")
public class PlaylistDTO {

    @Schema(description = "播放列表ID")
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "列表名称")
    private String name;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "是否默认列表: 0-否, 1-是")
    private Integer isDefault;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;

    @Schema(description = "歌曲数量")
    private Integer trackCount;

    @Schema(description = "歌曲列表")
    private List<MusicDTO> tracks;
}