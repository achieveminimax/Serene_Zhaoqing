package com.zqtravel.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 令牌响应对象
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@Schema(description = "令牌响应")
public class TokenResponse {

    @Schema(description = "访问令牌", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @Schema(description = "刷新令牌", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String refreshToken;

    @Schema(description = "令牌类型", example = "Bearer")
    private String tokenType = "Bearer";

    @Schema(description = "访问令牌过期时间（秒）", example = "7200")
    private Long expiresIn;

    @Schema(description = "刷新令牌过期时间（秒）", example = "604800")
    private Long refreshExpiresIn;

    @Schema(description = "用户信息")
    private UserDTO user;
}