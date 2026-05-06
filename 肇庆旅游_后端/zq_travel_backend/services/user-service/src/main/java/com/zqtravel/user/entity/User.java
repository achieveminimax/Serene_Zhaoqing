package com.zqtravel.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类
 * 对应数据库表: users
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@TableName("users")
public class User {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 微信OpenID
     */
    private String openid;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像URL
     */
    @TableField("avatar_url")
    private String avatarUrl;

    /**
     * 性别 0-未知 1-男 2-女
     */
    private Integer gender;

    /**
     * 状态 0-禁用 1-正常
     */
    private Integer status;

    /**
     * 最后登录时间
     */
    @TableField("last_login_at")
    private LocalDateTime lastLoginAt;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    /**
     * 是否删除 0-否 1-是
     */
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;
}