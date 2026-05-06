package com.zqtravel.user.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zqtravel.user.entity.UserAuth;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户认证数据访问接口
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Mapper
public interface UserAuthRepository extends BaseMapper<UserAuth> {

    /**
     * 根据认证类型和认证标识查询认证信息
     * 
     * @param authType 认证类型
     * @param authKey 认证标识
     * @return 用户认证信息
     */
    UserAuth selectByAuthTypeAndKey(String authType, String authKey);

    /**
     * 根据用户ID和认证类型查询认证信息
     * 
     * @param userId 用户ID
     * @param authType 认证类型
     * @return 用户认证信息
     */
    UserAuth selectByUserIdAndAuthType(Long userId, String authType);
}