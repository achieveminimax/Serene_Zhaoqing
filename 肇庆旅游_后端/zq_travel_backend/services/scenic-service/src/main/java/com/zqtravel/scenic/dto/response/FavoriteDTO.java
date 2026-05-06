package com.zqtravel.scenic.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 收藏响应DTO
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@Schema(description = "收藏响应DTO")
public class FavoriteDTO {

    @Schema(description = "收藏ID", example = "1")
    private Long id;

    @Schema(description = "用户ID", example = "1")
    private Long userId;

    @Schema(description = "收藏类型：scenic/music/recipe/product", example = "scenic")
    private String targetType;

    @Schema(description = "目标ID", example = "1")
    private Long targetId;

    @Schema(description = "目标名称（如景点名称）", example = "七星岩")
    private String targetName;

    @Schema(description = "目标图片URL", example = "https://example.com/images/qixingyan.jpg")
    private String targetImage;

    @Schema(description = "目标描述", example = "七星岩是肇庆著名的自然景观...")
    private String targetDescription;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
}