package com.zqtravel.user.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zqtravel.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户数据访问接口
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Mapper
public interface UserRepository extends BaseMapper<User> {

    /**
     * 根据手机号查询用户
     * 
     * @param phone 手机号
     * @return 用户信息
     */
    User selectByPhone(String phone);

    /**
     * 根据OpenID查询用户
     * 
     * @param openid 微信OpenID
     * @return 用户信息
     */
    User selectByOpenid(String openid);
}