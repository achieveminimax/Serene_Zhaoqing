-- 商店模块测试数据
-- 版本: V1.0.1
-- 描述: 插入商品分类和商品测试数据

-- 插入商品分类测试数据（如果表中没有数据）
INSERT IGNORE INTO product_categories (id, name, parent_id, icon, sort_order) VALUES
(1, '肇庆特产', 0, 'https://example.com/icons/specialty.png', 1),
(2, '旅游纪念品', 0, 'https://example.com/icons/souvenir.png', 2),
(3, '健康食品', 0, 'https://example.com/icons/health-food.png', 3),
(4, '手工艺品', 0, 'https://example.com/icons/handicraft.png', 4),
(5, '茶叶', 1, 'https://example.com/icons/tea.png', 1),
(6, '糕点', 1, 'https://example.com/icons/cake.png', 2),
(7, '文创产品', 2, 'https://example.com/icons/cultural.png', 1),
(8, '传统工艺品', 4, 'https://example.com/icons/traditional.png', 1);

-- 插入商品测试数据
INSERT IGNORE INTO products (id, name, category_id, description, images, price, original_price, stock, sales_count, status) VALUES
(1, '肇庆裹蒸粽', 6, '肇庆传统特产，糯米包裹绿豆、猪肉等馅料，香气扑鼻，口感软糯', '["https://example.com/images/zongzi1.jpg", "https://example.com/images/zongzi2.jpg"]', 25.00, 30.00, 100, 50, 1),
(2, '端砚', 8, '中国四大名砚之一，石质细腻，发墨快，不损毫，砚中珍品', '["https://example.com/images/inkstone1.jpg", "https://example.com/images/inkstone2.jpg"]', 280.00, 350.00, 20, 8, 1),
(3, '鼎湖山茶饼', 5, '鼎湖山特产茶叶，香气浓郁，口感醇厚，回味甘甜', '["https://example.com/images/tea1.jpg", "https://example.com/images/tea2.jpg"]', 68.00, 80.00, 150, 30, 1),
(4, '七星岩纪念明信片', 7, '七星岩风景明信片套装，包含10张不同角度的美景', '["https://example.com/images/postcard1.jpg"]', 15.00, 20.00, 200, 45, 1),
(5, '肇庆草编工艺品', 8, '手工编织的草编工艺品，造型精美，具有地方特色', '["https://example.com/images/grass1.jpg"]', 45.00, 60.00, 50, 12, 1),
(6, '鼎湖山蜂蜜', 3, '鼎湖山天然蜂蜜，纯正无添加，营养丰富', '["https://example.com/images/honey1.jpg"]', 38.00, 45.00, 80, 25, 1),
(7, '肇庆旅游地图', 7, '详细的肇庆旅游地图，标注主要景点和路线', '["https://example.com/images/map1.jpg"]', 8.00, 10.00, 300, 60, 1),
(8, '传统竹编篮', 8, '手工竹编篮，工艺精湛，实用美观', '["https://example.com/images/basket1.jpg"]', 75.00, 90.00, 30, 5, 1);

-- 插入购物车测试数据（可选，用于测试）
-- 注意：需要先有用户数据，这里假设用户ID 1001存在
INSERT IGNORE INTO carts (user_id, product_id, quantity, selected) VALUES
(1001, 1, 2, 1),
(1001, 3, 1, 1),
(1001, 6, 3, 0);

-- 创建订单号生成函数（MySQL 8.0+）
-- 如果函数已存在则忽略
DELIMITER //
CREATE FUNCTION IF NOT EXISTS generate_order_no() RETURNS VARCHAR(64)
DETERMINISTIC
BEGIN
    DECLARE order_no VARCHAR(64);
    DECLARE timestamp_str VARCHAR(20);
    DECLARE random_str VARCHAR(4);
    
    SET timestamp_str = DATE_FORMAT(NOW(), '%Y%m%d%H%i%s');
    SET random_str = LPAD(FLOOR(RAND() * 10000), 4, '0');
    SET order_no = CONCAT('ORD', timestamp_str, random_str);
    
    RETURN order_no;
END //
DELIMITER ;