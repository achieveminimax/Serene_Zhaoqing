package com.zqtravel.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 更新用户信息请求参数
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@Schema(description = "更新用户信息请求")
public class UpdateUserRequest {

    @Schema(description = "昵称", example = "新的昵称")
    @Size(max = 100, message = "昵称长度不能超过100个字符")
    private String nickname;

    @Schema(description = "头像URL", example = "https://example.com/new-avatar.jpg")
    @Size(max = 500, message = "头像URL长度不能超过500个字符")
    private String avatarUrl;

    @Schema(description = "性别 0-未知 1-男 2-女", example = "1")
    private Integer gender;
}