package com.zqtravel.user.service;

import com.zqtravel.user.dto.LoginRequest;
import com.zqtravel.user.dto.RegisterRequest;
import com.zqtravel.user.dto.TokenResponse;
import com.zqtravel.user.dto.UserDTO;

/**
 * 认证服务接口
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
public interface AuthService {

    /**
     * 用户注册
     * 
     * @param request 注册请求参数
     * @return 注册成功的用户信息
     */
    UserDTO register(RegisterRequest request);

    /**
     * 用户登录
     * 
     * @param request 登录请求参数
     * @return 令牌响应
     */
    TokenResponse login(LoginRequest request);

    /**
     * 用户登出
     * 
     * @param token 访问令牌
     */
    void logout(String token);

    /**
     * 刷新令牌
     * 
     * @param refreshToken 刷新令牌
     * @return 新的令牌响应
     */
    TokenResponse refreshToken(String refreshToken);

    /**
     * 获取当前登录用户信息
     * 
     * @return 用户信息
     */
    UserDTO getCurrentUser();
}