-- 用户模块表结构
-- 版本: V1.0.0
-- 描述: 创建用户相关表结构

-- 用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    openid VARCHAR(128) UNIQUE COMMENT '微信OpenID',
    phone VARCHAR(20) UNIQUE COMMENT '手机号',
    nickname VARCHAR(100) COMMENT '昵称',
    avatar_url VARCHAR(500) COMMENT '头像URL',
    gender TINYINT DEFAULT 0 COMMENT '性别 0-未知 1-男 2-女',
    status TINYINT DEFAULT 1 COMMENT '状态 0-禁用 1-正常',
    last_login_at DATETIME COMMENT '最后登录时间',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT DEFAULT 0 COMMENT '是否删除 0-否 1-是',
    INDEX idx_openid (openid),
    INDEX idx_phone (phone),
    INDEX idx_created_at (created_at),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 用户认证表
CREATE TABLE IF NOT EXISTS user_auths (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    auth_type VARCHAR(20) NOT NULL COMMENT '认证类型: wechat/phone',
    auth_key VARCHAR(128) NOT NULL COMMENT '认证标识: openid/phone',
    auth_secret VARCHAR(255) COMMENT '密码/凭证',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user_id (user_id),
    INDEX idx_auth_key (auth_key),
    UNIQUE KEY uk_auth (auth_type, auth_key),
    CONSTRAINT fk_user_auths_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户认证表';

-- 初始化管理员用户（可选）
-- INSERT INTO users (openid, phone, nickname, avatar_url, gender, status) 
-- VALUES ('admin_openid', '13800138000', '系统管理员', 'https://example.com/avatar.jpg', 0, 1);