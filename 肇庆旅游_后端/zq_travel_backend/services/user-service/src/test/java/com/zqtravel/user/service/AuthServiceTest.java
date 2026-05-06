package com.zqtravel.user.service;

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
import com.zqtravel.user.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * 认证服务单元测试
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private UserAuthRepository userAuthRepository;
    
    @Mock
    private UserService userService;
    
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    
    @Mock
    private AuthenticationManager authenticationManager;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private TokenBlacklistService tokenBlacklistService;
    
    @InjectMocks
    private AuthServiceImpl authService;
    
    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User user;
    private UserAuth userAuth;
    
    @BeforeEach
    void setUp() {
        // 初始化测试数据
        registerRequest = new RegisterRequest();
        registerRequest.setPhone("13800138000");
        registerRequest.setPassword("password123");
        registerRequest.setNickname("测试用户");
        
        loginRequest = new LoginRequest();
        loginRequest.setPhone("13800138000");
        loginRequest.setPassword("password123");
        
        user = new User();
        user.setId(1L);
        user.setPhone("13800138000");
        user.setNickname("测试用户");
        user.setStatus(1);
        user.setGender(0);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        userAuth = new UserAuth();
        userAuth.setId(1L);
        userAuth.setUserId(1L);
        userAuth.setAuthType("phone");
        userAuth.setAuthKey("13800138000");
        userAuth.setAuthSecret("encodedPassword");
    }
    
    @Test
    void testRegister_Success() {
        // 模拟行为
        when(userRepository.selectByPhone(anyString())).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.insert(any(User.class))).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(1L);
            return 1;
        });
        when(userAuthRepository.insert(any(UserAuth.class))).thenReturn(1);
        
        // 执行测试
        UserDTO result = authService.register(registerRequest);
        
        // 验证结果
        assertNotNull(result);
        assertEquals("13800138000", result.getPhone());
        assertEquals("测试用户", result.getNickname());
        
        // 验证交互
        verify(userRepository).selectByPhone("13800138000");
        verify(userRepository).insert(any(User.class));
        verify(userAuthRepository).insert(any(UserAuth.class));
        verify(passwordEncoder).encode("password123");
    }
    
    @Test
    void testRegister_PhoneAlreadyExists() {
        // 模拟手机号已存在
        when(userRepository.selectByPhone(anyString())).thenReturn(user);
        
        // 执行测试并验证异常
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.register(registerRequest);
        });
        
        assertEquals("手机号已注册", exception.getMessage());
        
        // 验证交互
        verify(userRepository).selectByPhone("13800138000");
        verify(userRepository, never()).insert(any(User.class));
        verify(userAuthRepository, never()).insert(any(UserAuth.class));
    }
    
    @Test
    void testLogin_Success() {
        // 模拟行为
        Authentication authentication = mock(Authentication.class);
        CustomUserDetails userDetails = new CustomUserDetails(
            1L, "13800138000", "encodedPassword", null, true, true, true, true);
        
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtTokenProvider.generateAccessToken(anyString(), anyLong()))
            .thenReturn("accessToken");
        when(jwtTokenProvider.generateRefreshToken(anyString(), anyLong()))
            .thenReturn("refreshToken");
        when(jwtTokenProvider.getAccessTokenValidity()).thenReturn(7200000L);
        when(jwtTokenProvider.getRefreshTokenValidity()).thenReturn(604800000L);
        
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setPhone("13800138000");
        when(userService.getUserById(1L)).thenReturn(userDTO);
        
        // 执行测试
        TokenResponse result = authService.login(loginRequest);
        
        // 验证结果
        assertNotNull(result);
        assertEquals("accessToken", result.getAccessToken());
        assertEquals("refreshToken", result.getRefreshToken());
        assertEquals(7200L, result.getExpiresIn());
        assertEquals(604800L, result.getRefreshExpiresIn());
        assertNotNull(result.getUser());
        
        // 验证交互
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userService).updateLastLoginTime(1L);
        verify(jwtTokenProvider).generateAccessToken("13800138000", 1L);
        verify(jwtTokenProvider).generateRefreshToken("13800138000", 1L);
        verify(userService).getUserById(1L);
    }
    
    @Test
    void testLogout_Success() {
        // 模拟行为
        String token = "testToken";
        when(jwtTokenProvider.validateToken(token)).thenReturn(true);
        when(jwtTokenProvider.getExpirationDateFromToken(token))
            .thenReturn(new java.util.Date(System.currentTimeMillis() + 3600000));
        
        // 执行测试
        authService.logout(token);
        
        // 验证交互
        verify(jwtTokenProvider).validateToken(token);
        verify(jwtTokenProvider).getExpirationDateFromToken(token);
        verify(tokenBlacklistService).addToBlacklist(eq(token), anyLong());
    }
    
    @Test
    void testRefreshToken_Success() {
        // 模拟行为
        String refreshToken = "refreshToken";
        when(jwtTokenProvider.validateToken(refreshToken)).thenReturn(true);
        when(jwtTokenProvider.getUsernameFromToken(refreshToken)).thenReturn("13800138000");
        when(jwtTokenProvider.getUserIdFromToken(refreshToken)).thenReturn(1L);
        when(jwtTokenProvider.generateAccessToken(anyString(), anyLong()))
            .thenReturn("newAccessToken");
        when(jwtTokenProvider.generateRefreshToken(anyString(), anyLong()))
            .thenReturn("newRefreshToken");
        when(jwtTokenProvider.getAccessTokenValidity()).thenReturn(7200000L);
        when(jwtTokenProvider.getRefreshTokenValidity()).thenReturn(604800000L);
        
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setPhone("13800138000");
        when(userService.getUserById(1L)).thenReturn(userDTO);
        
        // 执行测试
        TokenResponse result = authService.refreshToken(refreshToken);
        
        // 验证结果
        assertNotNull(result);
        assertEquals("newAccessToken", result.getAccessToken());
        assertEquals("newRefreshToken", result.getRefreshToken());
        assertEquals(7200L, result.getExpiresIn());
        assertEquals(604800L, result.getRefreshExpiresIn());
        assertNotNull(result.getUser());
        
        // 验证交互
        verify(jwtTokenProvider).validateToken(refreshToken);
        verify(jwtTokenProvider).getUsernameFromToken(refreshToken);
        verify(jwtTokenProvider).getUserIdFromToken(refreshToken);
        verify(jwtTokenProvider).generateAccessToken("13800138000", 1L);
        verify(jwtTokenProvider).generateRefreshToken("13800138000", 1L);
        verify(userService).getUserById(1L);
    }
}