package com.zqtravel.user.security;

import com.zqtravel.user.entity.User;
import com.zqtravel.user.entity.UserAuth;
import com.zqtravel.user.repository.UserAuthRepository;
import com.zqtravel.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * 自定义用户详情服务
 * 实现Spring Security的UserDetailsService接口
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserAuthRepository userAuthRepository;

    /**
     * 根据用户名（手机号）加载用户详情
     * 
     * @param username 用户名（手机号）
     * @return 用户详情
     * @throws UsernameNotFoundException 用户未找到异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据手机号查询用户
        User user = userRepository.selectByPhone(username);
        
        if (user == null) {
            log.error("用户未找到: {}", username);
            throw new UsernameNotFoundException("用户未找到: " + username);
        }
        
        // 检查用户状态
        if (user.getStatus() != null && user.getStatus() == 0) {
            log.error("用户已被禁用: {}", username);
            throw new UsernameNotFoundException("用户已被禁用: " + username);
        }
        
        // 查询用户认证信息（手机号认证）
        UserAuth userAuth = userAuthRepository.selectByUserIdAndAuthType(user.getId(), "phone");
        if (userAuth == null) {
            log.error("用户认证信息未找到，用户ID: {}, 认证类型: phone", user.getId());
            throw new UsernameNotFoundException("用户认证信息未找到");
        }
        
        // 构建UserDetails对象
        return new CustomUserDetails(
            user.getId(),
            user.getPhone(),
            userAuth.getAuthSecret(), // 密码（加密后的）
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
            user.getStatus() == 1,
            true,
            true,
            true
        );
    }
}