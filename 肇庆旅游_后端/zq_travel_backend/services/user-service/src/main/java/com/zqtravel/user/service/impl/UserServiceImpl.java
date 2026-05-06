package com.zqtravel.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zqtravel.user.dto.UpdateUserRequest;
import com.zqtravel.user.dto.UserDTO;
import com.zqtravel.user.entity.User;
import com.zqtravel.user.repository.UserRepository;
import com.zqtravel.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 用户服务实现类
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * 根据用户ID获取用户信息
     */
    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在，用户ID: " + userId);
        }
        
        // 检查用户是否被删除
        if (user.getIsDeleted() != null && user.getIsDeleted() == 1) {
            throw new RuntimeException("用户已被删除，用户ID: " + userId);
        }
        
        return convertToDTO(user);
    }

    /**
     * 根据手机号获取用户信息
     */
    @Override
    public UserDTO getUserByPhone(String phone) {
        User user = userRepository.selectByPhone(phone);
        if (user == null) {
            throw new RuntimeException("用户不存在，手机号: " + phone);
        }
        
        // 检查用户是否被删除
        if (user.getIsDeleted() != null && user.getIsDeleted() == 1) {
            throw new RuntimeException("用户已被删除，手机号: " + phone);
        }
        
        return convertToDTO(user);
    }

    /**
     * 更新用户信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDTO updateUser(Long userId, UpdateUserRequest request) {
        User user = userRepository.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在，用户ID: " + userId);
        }
        
        // 检查用户是否被删除
        if (user.getIsDeleted() != null && user.getIsDeleted() == 1) {
            throw new RuntimeException("用户已被删除，用户ID: " + userId);
        }
        
        // 更新用户信息
        boolean updated = false;
        
        if (request.getNickname() != null) {
            user.setNickname(request.getNickname());
            updated = true;
        }
        
        if (request.getAvatarUrl() != null) {
            user.setAvatarUrl(request.getAvatarUrl());
            updated = true;
        }
        
        if (request.getGender() != null) {
            user.setGender(request.getGender());
            updated = true;
        }
        
        if (updated) {
            userRepository.updateById(user);
            log.info("用户信息更新成功，用户ID: {}", userId);
        }
        
        return convertToDTO(user);
    }

    /**
     * 更新用户最后登录时间
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLastLoginTime(Long userId) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userId)
                    .set("last_login_at", LocalDateTime.now());
        
        userRepository.update(null, updateWrapper);
        log.debug("用户最后登录时间已更新，用户ID: {}", userId);
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