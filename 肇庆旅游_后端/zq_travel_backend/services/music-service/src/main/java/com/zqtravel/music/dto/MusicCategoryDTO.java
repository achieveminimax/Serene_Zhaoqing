package com.zqtravel.music.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 音乐分类数据传输对象
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@Schema(description = "音乐分类信息")
public class MusicCategoryDTO {

    @Schema(description = "分类ID")
    private Long id;

    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "类型: relax/nature/meditation/sleep")
    private String type;

    @Schema(description = "图标URL")
    private String icon;

    @Schema(description = "排序")
    private Integer sortOrder;

    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
}