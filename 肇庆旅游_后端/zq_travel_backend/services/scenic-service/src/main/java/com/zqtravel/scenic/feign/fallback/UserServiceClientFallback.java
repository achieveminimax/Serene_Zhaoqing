package com.zqtravel.scenic.feign.fallback;

import com.zqtravel.common.core.model.ApiResponse;
import com.zqtravel.scenic.feign.UserServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 用户服务Feign客户端降级处理类
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Slf4j
@Component
public class UserServiceClientFallback implements UserServiceClient {

    @Override
    public ApiResponse<Object> getUserById(Long userId, String authorization) {
        log.warn("用户服务不可用，降级处理 - getUserById, userId: {}", userId);
        return ApiResponse.error("500", "用户服务暂时不可用，请稍后重试");
    }

    @Override
    public ApiResponse<Object> validateToken(String token) {
        log.warn("用户服务不可用，降级处理 - validateToken");
        return ApiResponse.error("500", "用户服务暂时不可用，请稍后重试");
    }

    @Override
    public ApiResponse<Object> getCurrentUser(String authorization) {
        log.warn("用户服务不可用，降级处理 - getCurrentUser");
        return ApiResponse.error("500", "用户服务暂时不可用，请稍后重试");
    }
}