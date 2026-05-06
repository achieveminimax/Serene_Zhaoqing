package com.zqtravel.music.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 音乐数据传输对象
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@Schema(description = "音乐信息")
public class MusicDTO {

    @Schema(description = "音乐ID")
    private Long id;

    @Schema(description = "音乐名称")
    private String name;

    @Schema(description = "艺术家")
    private String artist;

    @Schema(description = "表情符号")
    private String emoji;

    @Schema(description = "标签")
    private String tag;

    @Schema(description = "时长(秒)")
    private Integer duration;

    @Schema(description = "封面图URL")
    private String imageUrl;

    @Schema(description = "音频文件URL")
    private String audioUrl;

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "歌词")
    private String lyrics;

    @Schema(description = "播放次数")
    private Integer playCount;

    @Schema(description = "收藏次数")
    private Integer favoriteCount;

    @Schema(description = "状态: 0-下架, 1-上架")
    private Integer status;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;

    @Schema(description = "是否已收藏（当前用户）")
    private Boolean isFavorited;
}