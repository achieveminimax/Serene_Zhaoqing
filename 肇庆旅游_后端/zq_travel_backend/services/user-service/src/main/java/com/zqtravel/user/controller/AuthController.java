package com.zqtravel.user.controller;

import com.zqtravel.user.dto.LoginRequest;
import com.zqtravel.user.dto.RegisterRequest;
import com.zqtravel.user.dto.TokenResponse;
import com.zqtravel.user.dto.UpdateUserRequest;
import com.zqtravel.user.dto.UserDTO;
import com.zqtravel.user.service.AuthService;
import com.zqtravel.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 * 处理用户注册、登录、登出、刷新令牌等认证相关接口
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "认证管理", description = "用户认证相关接口")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    /**
     * 用户注册
     */
    @Operation(summary = "用户注册", description = "新用户注册接口")
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(
            @Valid @RequestBody RegisterRequest request) {
        
        log.info("用户注册请求，手机号: {}", request.getPhone());
        
        UserDTO user = authService.register(request);
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "注册成功");
        response.put("data", user);
        response.put("timestamp", System.currentTimeMillis() / 1000);
        
        return ResponseEntity.ok(response);
    }

    /**
     * 用户登录
     */
    @Operation(summary = "用户登录", description = "用户登录接口")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @Valid @RequestBody LoginRequest request) {
        
        log.info("用户登录请求，手机号: {}", request.getPhone());
        
        TokenResponse tokenResponse = authService.login(request);
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "登录成功");
        response.put("data", tokenResponse);
        response.put("timestamp", System.currentTimeMillis() / 1000);
        
        return ResponseEntity.ok(response);
    }

    /**
     * 用户登出
     */
    @Operation(summary = "用户登出", description = "用户登出接口")
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpServletRequest request) {
        
        String token = extractToken(request);
        if (token == null) {
            throw new RuntimeException("未找到访问令牌");
        }
        
        authService.logout(token);
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "登出成功");
        response.put("data", null);
        response.put("timestamp", System.currentTimeMillis() / 1000);
        
        return ResponseEntity.ok(response);
    }

    /**
     * 刷新令牌
     */
    @Operation(summary = "刷新令牌", description = "使用Refresh Token刷新Access Token")
    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, Object>> refreshToken(
            @Parameter(description = "刷新令牌", required = true)
            @RequestParam String refreshToken) {
        
        log.info("刷新令牌请求");
        
        TokenResponse tokenResponse = authService.refreshToken(refreshToken);
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "令牌刷新成功");
        response.put("data", tokenResponse);
        response.put("timestamp", System.currentTimeMillis() / 1000);
        
        return ResponseEntity.ok(response);
    }

    /**
     * 获取当前用户信息
     */
    @Operation(summary = "获取用户信息", description = "获取当前登录用户的详细信息")
    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getProfile() {
        
        UserDTO user = authService.getCurrentUser();
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "获取用户信息成功");
        response.put("data", user);
        response.put("timestamp", System.currentTimeMillis() / 1000);
        
        return ResponseEntity.ok(response);
    }

    /**
     * 更新用户信息
     */
    @Operation(summary = "更新用户信息", description = "更新当前登录用户的个人信息")
    @PutMapping("/profile")
    public ResponseEntity<Map<String, Object>> updateProfile(
            @Valid @RequestBody UpdateUserRequest request) {
        
        UserDTO currentUser = authService.getCurrentUser();
        
        // 调用UserService更新用户信息
        UserDTO updatedUser = userService.updateUser(currentUser.getId(), request);
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "更新用户信息成功");
        response.put("data", updatedUser);
        response.put("timestamp", System.currentTimeMillis() / 1000);
        
        return ResponseEntity.ok(response);
    }

    /**
     * 从请求中提取JWT令牌
     */
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}