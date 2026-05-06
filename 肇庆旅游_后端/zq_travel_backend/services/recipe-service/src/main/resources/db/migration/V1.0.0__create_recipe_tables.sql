-- 食谱模块表结构
-- 版本: V1.0.0
-- 描述: 创建食谱相关表结构

-- 食谱分类表
CREATE TABLE IF NOT EXISTS recipe_categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(100) NOT NULL COMMENT '分类名称',
    icon VARCHAR(200) COMMENT '图标',
    sort_order INT DEFAULT 0 COMMENT '排序',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_sort (sort_order)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='食谱分类表';

-- 食谱表
CREATE TABLE IF NOT EXISTS recipes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    name VARCHAR(200) NOT NULL COMMENT '食谱名称',
    category_id BIGINT COMMENT '分类ID',
    image_url VARCHAR(500) COMMENT '封面图URL',
    description TEXT COMMENT '描述',
    ingredients JSON COMMENT '食材列表',
    steps JSON COMMENT '步骤列表',
    calories INT COMMENT '卡路里',
    cook_time INT COMMENT '烹饪时间(分钟)',
    difficulty VARCHAR(20) COMMENT '难度',
    tags JSON COMMENT '标签',
    view_count INT DEFAULT 0 COMMENT '浏览次数',
    favorite_count INT DEFAULT 0 COMMENT '收藏次数',
    status TINYINT DEFAULT 1 COMMENT '状态',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT DEFAULT 0 COMMENT '是否删除',
    INDEX idx_category (category_id),
    INDEX idx_status (status),
    INDEX idx_difficulty (difficulty),
    INDEX idx_created_at (created_at),
    CONSTRAINT fk_recipes_category FOREIGN KEY (category_id) REFERENCES recipe_categories(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='食谱表';

-- 烹饪记录表
CREATE TABLE IF NOT EXISTS cooking_records (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    recipe_id BIGINT NOT NULL COMMENT '食谱ID',
    cooked_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '烹饪时间',
    note TEXT COMMENT '备注',
    rating INT COMMENT '评分',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user (user_id),
    INDEX idx_recipe (recipe_id),
    INDEX idx_cooked_at (cooked_at),
    INDEX idx_rating (rating),
    CONSTRAINT fk_cooking_records_recipe FOREIGN KEY (recipe_id) REFERENCES recipes(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='烹饪记录表';

-- 初始化食谱分类
INSERT INTO recipe_categories (name, icon, sort_order) VALUES
('中式菜肴', 'https://example.com/icons/chinese.png', 1),
('西式美食', 'https://example.com/icons/western.png', 2),
('健康轻食', 'https://example.com/icons/healthy.png', 3),
('甜品饮品', 'https://example.com/icons/dessert.png', 4),
('快手简餐', 'https://example.com/icons/quick.png', 5);