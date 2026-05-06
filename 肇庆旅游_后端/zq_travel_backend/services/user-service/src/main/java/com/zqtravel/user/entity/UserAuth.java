package com.zqtravel.user.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户认证实体类
 * 对应数据库表: user_auths
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Data
@TableName("user_auths")
public class UserAuth {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 认证类型: wechat/phone
     */
    @TableField("auth_type")
    private String authType;

    /**
     * 认证标识: openid/phone
     */
    @TableField("auth_key")
    private String authKey;

    /**
     * 密码/凭证
     */
    @TableField("auth_secret")
    private String authSecret;

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
}