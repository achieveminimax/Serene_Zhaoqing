package com.zqtravel.music.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Min;

/**
 * 分页请求参数
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@Schema(description = "分页请求参数")
public class PageRequest {

    @Schema(description = "页码，从1开始", example = "1", defaultValue = "1")
    @Min(value = 1, message = "页码必须大于0")
    private Integer page = 1;

    @Schema(description = "每页大小", example = "20", defaultValue = "20")
    @Min(value = 1, message = "每页大小必须大于0")
    private Integer size = 20;
}