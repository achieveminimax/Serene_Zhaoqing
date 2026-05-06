package com.zqtravel.user.service;

import com.zqtravel.user.dto.UpdateUserRequest;
import com.zqtravel.user.dto.UserDTO;

/**
 * 用户服务接口
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
public interface UserService {

    /**
     * 根据用户ID获取用户信息
     * 
     * @param userId 用户ID
     * @return 用户信息
     */
    UserDTO getUserById(Long userId);

    /**
     * 根据手机号获取用户信息
     * 
     * @param phone 手机号
     * @return 用户信息
     */
    UserDTO getUserByPhone(String phone);

    /**
     * 更新用户信息
     * 
     * @param userId 用户ID
     * @param request 更新请求参数
     * @return 更新后的用户信息
     */
    UserDTO updateUser(Long userId, UpdateUserRequest request);

    /**
     * 更新用户最后登录时间
     * 
     * @param userId 用户ID
     */
    void updateLastLoginTime(Long userId);
}