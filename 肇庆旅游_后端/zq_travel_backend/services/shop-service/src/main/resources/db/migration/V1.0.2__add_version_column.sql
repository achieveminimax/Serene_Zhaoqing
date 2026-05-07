-- 添加乐观锁版本号字段
-- 版本: V1.0.2
-- 描述: 为products表添加version字段，用于乐观锁控制

-- 为products表添加version字段
ALTER TABLE products ADD COLUMN IF NOT EXISTS version INT DEFAULT 0 COMMENT '乐观锁版本号' AFTER is_deleted;

-- 更新现有数据的version字段为0
UPDATE products SET version = 0 WHERE version IS NULL;