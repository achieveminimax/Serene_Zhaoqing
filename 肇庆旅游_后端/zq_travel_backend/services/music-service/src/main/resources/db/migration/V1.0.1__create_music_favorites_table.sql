-- 音乐收藏表
-- 版本: V1.0.1
-- 描述: 创建音乐收藏表，用于存储用户收藏的音乐

CREATE TABLE IF NOT EXISTS music_favorites (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    music_id BIGINT NOT NULL COMMENT '音乐ID',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user (user_id),
    INDEX idx_music (music_id),
    UNIQUE KEY uk_user_music (user_id, music_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='音乐收藏表';