package com.zqtravel.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zqtravel.user.dto.LoginRequest;
import com.zqtravel.user.dto.RegisterRequest;
import com.zqtravel.user.dto.UserDTO;
import com.zqtravel.user.service.AuthService;
import com.zqtravel.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 认证控制器集成测试
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private UserService userService;

    @Test
    void testRegister_Success() throws Exception {
        // 准备测试数据
        RegisterRequest request = new RegisterRequest();
        request.setPhone("13800138000");
        request.setPassword("password123");
        request.setNickname("测试用户");

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setPhone("13800138000");
        userDTO.setNickname("测试用户");

        // 模拟服务层行为
        when(authService.register(any(RegisterRequest.class))).thenReturn(userDTO);

        // 执行请求并验证
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("注册成功"))
                .andExpect(jsonPath("$.data.phone").value("13800138000"))
                .andExpect(jsonPath("$.data.nickname").value("测试用户"));
    }

    @Test
    void testLogin_Success() throws Exception {
        // 准备测试数据
        LoginRequest request = new LoginRequest();
        request.setPhone("13800138000");
        request.setPassword("password123");

        // 模拟服务层行为
        when(authService.login(any(LoginRequest.class))).thenReturn(new com.zqtravel.user.dto.TokenResponse());

        // 执行请求并验证
        mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("登录成功"));
    }

    @Test
    void testRegister_ValidationError() throws Exception {
        // 准备无效的测试数据
        RegisterRequest request = new RegisterRequest();
        request.setPhone("123"); // 无效的手机号
        request.setPassword("123"); // 密码太短

        // 执行请求并验证
        mockMvc.perform(post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}