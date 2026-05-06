package com.zqtravel.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zqtravel.user.dto.LoginRequest;
import com.zqtravel.user.dto.RegisterRequest;
import com.zqtravel.user.dto.TokenResponse;
import com.zqtravel.user.dto.UserDTO;
import com.zqtravel.user.entity.User;
import com.zqtravel.user.entity.UserAuth;
import com.zqtravel.user.repository.UserAuthRepository;
import com.zqtravel.user.repository.UserRepository;
import com.zqtravel.user.security.CustomUserDetails;
import com.zqtravel.user.security.JwtTokenProvider;
import com.zqtravel.user.security.TokenBlacklistService;
import com.zqtravel.user.service.AuthService;
import com.zqtravel.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 认证服务实现类
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserAuthRepository userAuthRepository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenBlacklistService tokenBlacklistService;

    /**
     * 用户注册
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDTO register(RegisterRequest request) {
        // 检查手机号是否已注册
        User existingUser = userRepository.selectByPhone(request.getPhone());
        if (existingUser != null) {
            throw new RuntimeException("手机号已注册");
        }

        // 创建用户
        User user = new User();
        user.setPhone(request.getPhone());
        user.setNickname(request.getNickname() != null ? request.getNickname() : "用户_" + request.getPhone().substring(7));
        user.setStatus(1); // 正常状态
        user.setGender(0); // 未知性别
        
        userRepository.insert(user);
        log.info("用户注册成功，用户ID: {}", user.getId());

        // 创建认证信息
        UserAuth userAuth = new UserAuth();
        userAuth.setUserId(user.getId());
        userAuth.setAuthType("phone");
        userAuth.setAuthKey(request.getPhone());
        userAuth.setAuthSecret(passwordEncoder.encode(request.getPassword()));
        
        userAuthRepository.insert(userAuth);
        log.info("用户认证信息创建成功，用户ID: {}", user.getId());

        // 转换为DTO返回
        return convertToDTO(user);
    }

    /**
     * 用户登录
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TokenResponse login(LoginRequest request) {
        // 使用Spring Security进行认证
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getPhone(),
                request.getPassword()
            )
        );

        // 获取认证后的用户详情
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        
        // 更新最后登录时间
        userService.updateLastLoginTime(userDetails.getUserId());

        // 生成令牌
        String accessToken = jwtTokenProvider.generateAccessToken(userDetails.getUsername(), userDetails.getUserId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(userDetails.getUsername(), userDetails.getUserId());

        // 构建响应
        TokenResponse response = new TokenResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setExpiresIn(jwtTokenProvider.getAccessTokenValidity() / 1000);
        response.setRefreshExpiresIn(jwtTokenProvider.getRefreshTokenValidity() / 1000);
        response.setUser(userService.getUserById(userDetails.getUserId()));

        log.info("用户登录成功，用户ID: {}", userDetails.getUserId());
        return response;
    }

    /**
     * 用户登出
     */
    @Override
    public void logout(String token) {
        // 验证令牌
        if (!jwtTokenProvider.validateToken(token)) {
            throw new RuntimeException("无效的令牌");
        }

        // 获取令牌过期时间
        long expirationTime = jwtTokenProvider.getExpirationDateFromToken(token).getTime();
        
        // 将令牌加入黑名单
        tokenBlacklistService.addToBlacklist(token, expirationTime);
        
        log.info("用户登出成功，令牌已加入黑名单");
    }

    /**
     * 刷新令牌
     */
    @Override
    public TokenResponse refreshToken(String refreshToken) {
        // 验证刷新令牌
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("无效的刷新令牌");
        }

        // 从令牌中提取用户信息
        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
        Long userId = jwtTokenProvider.getUserIdFromToken(refreshToken);

        // 生成新的令牌
        String newAccessToken = jwtTokenProvider.generateAccessToken(username, userId);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(username, userId);

        // 构建响应
        TokenResponse response = new TokenResponse();
        response.setAccessToken(newAccessToken);
        response.setRefreshToken(newRefreshToken);
        response.setExpiresIn(jwtTokenProvider.getAccessTokenValidity() / 1000);
        response.setRefreshExpiresIn(jwtTokenProvider.getRefreshTokenValidity() / 1000);
        response.setUser(userService.getUserById(userId));

        log.info("令牌刷新成功，用户ID: {}", userId);
        return response;
    }

    /**
     * 获取当前登录用户信息
     */
    @Override
    public UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("用户未认证");
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userService.getUserById(userDetails.getUserId());
    }

    /**
     * 将User实体转换为UserDTO
     */
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setOpenid(user.getOpenid());
        dto.setPhone(user.getPhone());
        dto.setNickname(user.getNickname());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setGender(user.getGender());
        dto.setStatus(user.getStatus());
        dto.setLastLoginAt(user.getLastLoginAt());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}