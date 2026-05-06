package com.zqtravel.scenic.feign;

import com.zqtravel.common.core.model.ApiResponse;
import com.zqtravel.scenic.feign.fallback.UserServiceClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 用户服务Feign客户端接口
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@FeignClient(
    name = "user-service",
    path = "/api/v1/users",
    fallback = UserServiceClientFallback.class
)
public interface UserServiceClient {

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @param authorization 授权令牌
     * @return 用户信息
     */
    @GetMapping("/{userId}")
    ApiResponse<Object> getUserById(
        @PathVariable("userId") Long userId,
        @RequestHeader(value = "Authorization", required = false) String authorization
    );

    /**
     * 验证用户令牌
     *
     * @param token 用户令牌
     * @return 验证结果
     */
    @GetMapping("/auth/validate")
    ApiResponse<Object> validateToken(
        @RequestHeader("Authorization") String token
    );

    /**
     * 获取当前用户信息
     *
     * @param authorization 授权令牌
     * @return 当前用户信息
     */
    @GetMapping("/me")
    ApiResponse<Object> getCurrentUser(
        @RequestHeader("Authorization") String authorization
    );
}