-- 景点模块表结构
-- 版本: V1.0.0
-- 描述: 创建景点相关表结构

-- 景点分类表
CREATE TABLE IF NOT EXISTS scenic_categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(100) NOT NULL COMMENT '分类名称',
    icon VARCHAR(200) COMMENT '图标URL',
    sort_order INT DEFAULT 0 COMMENT '排序',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_sort (sort_order)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='景点分类表';

-- 景点表
CREATE TABLE IF NOT EXISTS scenic_spots (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(200) NOT NULL COMMENT '景点名称',
    category_id BIGINT COMMENT '分类ID',
    hero_image VARCHAR(500) COMMENT '主图URL',
    images JSON COMMENT '图片列表',
    aqi INT COMMENT '空气质量指数',
    temperature DECIMAL(4,1) COMMENT '温度',
    humidity DECIMAL(4,1) COMMENT '湿度',
    quote VARCHAR(500) COMMENT '引用语',
    description TEXT COMMENT '描述',
    description_secondary TEXT COMMENT '次要描述',
    open_time VARCHAR(100) COMMENT '开放时间',
    difficulty VARCHAR(50) COMMENT '难度',
    distance VARCHAR(100) COMMENT '距离',
    location_lat DECIMAL(10,8) COMMENT '纬度',
    location_lng DECIMAL(11,8) COMMENT '经度',
    address VARCHAR(300) COMMENT '地址',
    status TINYINT DEFAULT 1 COMMENT '状态 0-下架 1-上架',
    view_count INT DEFAULT 0 COMMENT '浏览次数',
    favorite_count INT DEFAULT 0 COMMENT '收藏次数',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT DEFAULT 0 COMMENT '是否删除',
    INDEX idx_category (category_id),
    INDEX idx_status (status),
    INDEX idx_location (location_lat, location_lng),
    INDEX idx_created_at (created_at),
    FULLTEXT idx_search (name, description),
    CONSTRAINT fk_scenic_spots_category FOREIGN KEY (category_id) REFERENCES scenic_categories(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='景点表';

-- 用户收藏表（通用收藏，支持多种类型）
CREATE TABLE IF NOT EXISTS user_favorites (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    target_type VARCHAR(50) NOT NULL COMMENT '收藏类型: scenic/music/recipe/product',
    target_id BIGINT NOT NULL COMMENT '目标ID',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user (user_id),
    INDEX idx_target (target_type, target_id),
    UNIQUE KEY uk_user_target (user_id, target_type, target_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户收藏表';

-- 初始化默认分类
INSERT INTO scenic_categories (name, icon, sort_order) VALUES
('自然风光', 'https://example.com/icons/nature.png', 1),
('历史文化', 'https://example.com/icons/history.png', 2),
('休闲娱乐', 'https://example.com/icons/leisure.png', 3),
('美食购物', 'https://example.com/icons/food.png', 4);