-- 音乐模块表结构
-- 版本: V1.0.0
-- 描述: 创建音乐相关表结构

-- 音乐分类表
CREATE TABLE IF NOT EXISTS music_categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(100) NOT NULL COMMENT '分类名称',
    type VARCHAR(50) COMMENT '类型: relax/nature/meditation/sleep',
    icon VARCHAR(200) COMMENT '图标',
    sort_order INT DEFAULT 0 COMMENT '排序',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_type (type),
    INDEX idx_sort (sort_order)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='音乐分类表';

-- 音乐表
CREATE TABLE IF NOT EXISTS musics (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(200) NOT NULL COMMENT '音乐名称',
    artist VARCHAR(100) COMMENT '艺术家',
    emoji VARCHAR(10) COMMENT '表情符号',
    tag VARCHAR(50) COMMENT '标签',
    duration INT COMMENT '时长(秒)',
    image_url VARCHAR(500) COMMENT '封面图URL',
    audio_url VARCHAR(500) COMMENT '音频文件URL',
    category_id BIGINT COMMENT '分类ID',
    lyrics TEXT COMMENT '歌词',
    play_count INT DEFAULT 0 COMMENT '播放次数',
    favorite_count INT DEFAULT 0 COMMENT '收藏次数',
    status TINYINT DEFAULT 1 COMMENT '状态',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT DEFAULT 0 COMMENT '是否删除',
    INDEX idx_category (category_id),
    INDEX idx_status (status),
    INDEX idx_popularity (play_count, favorite_count),
    INDEX idx_created_at (created_at),
    CONSTRAINT fk_musics_category FOREIGN KEY (category_id) REFERENCES music_categories(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='音乐表';

-- 播放列表表
CREATE TABLE IF NOT EXISTS playlists (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    name VARCHAR(200) COMMENT '列表名称',
    description VARCHAR(500) COMMENT '描述',
    is_default TINYINT DEFAULT 0 COMMENT '是否默认列表',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user (user_id),
    INDEX idx_is_default (is_default)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='播放列表表';

-- 播放列表音乐关联表
CREATE TABLE IF NOT EXISTS playlist_tracks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    playlist_id BIGINT NOT NULL COMMENT '播放列表ID',
    music_id BIGINT NOT NULL COMMENT '音乐ID',
    sort_order INT DEFAULT 0 COMMENT '排序',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_playlist (playlist_id),
    UNIQUE KEY uk_playlist_music (playlist_id, music_id),
    CONSTRAINT fk_playlist_tracks_playlist FOREIGN KEY (playlist_id) REFERENCES playlists(id) ON DELETE CASCADE,
    CONSTRAINT fk_playlist_tracks_music FOREIGN KEY (music_id) REFERENCES musics(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='播放列表音乐关联表';

-- 初始化音乐分类
INSERT INTO music_categories (name, type, icon, sort_order) VALUES
('放松音乐', 'relax', 'https://example.com/icons/relax.png', 1),
('自然声音', 'nature', 'https://example.com/icons/nature.png', 2),
('冥想音乐', 'meditation', 'https://example.com/icons/meditation.png', 3),
('睡眠音乐', 'sleep', 'https://example.com/icons/sleep.png', 4),
('专注音乐', 'focus', 'https://example.com/icons/focus.png', 5);