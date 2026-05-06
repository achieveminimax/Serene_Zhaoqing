-- 测试数据：景点模块
-- 版本: V1.0.1
-- 描述: 插入测试景点数据

-- 注意：先确保分类已存在（V1.0.0中已初始化分类）

-- 插入测试景点
INSERT INTO scenic_spots (name, category_id, hero_image, images, aqi, temperature, humidity, quote, description, description_secondary, open_time, difficulty, distance, location_lat, location_lng, address, status, view_count, favorite_count) VALUES
('七星岩', 1, 'https://example.com/images/qixingyan.jpg', '["https://example.com/images/qixingyan1.jpg", "https://example.com/images/qixingyan2.jpg"]', 45, 25.5, 65.2, '桂林山水甲天下，肇庆七星岩更奇', '七星岩是肇庆著名的自然景观，由七座石灰岩山峰组成，形态各异，宛如北斗七星。', '景区内有多处溶洞和地下河，景色秀丽，是旅游观光的好去处。', '08:00-18:00', '中等', '距离市区5公里', 23.0516, 112.4587, '肇庆市端州区七星岩景区', 1, 1250, 320),
('鼎湖山', 1, 'https://example.com/images/dinghushan.jpg', '["https://example.com/images/dinghushan1.jpg"]', 35, 24.8, 70.1, '岭南第一山，鼎湖胜境', '鼎湖山是国家级自然保护区，以原始森林、瀑布群和佛教文化闻名。', '山中有庆云寺、飞水潭等著名景点，空气清新，负离子含量高。', '07:30-17:30', '容易', '距离市区18公里', 23.1667, 112.5167, '肇庆市鼎湖区鼎湖山景区', 1, 980, 210),
('星湖湿地公园', 3, 'https://example.com/images/xinghu.jpg', '[]', 40, 26.2, 68.5, '城市绿肺，候鸟天堂', '星湖湿地公园是城市中的天然氧吧，拥有丰富的水生植物和鸟类资源。', '适合休闲散步、观鸟和摄影，是市民休闲的好去处。', '全天开放', '容易', '距离市区2公里', 23.0589, 112.4722, '肇庆市端州区星湖大道', 1, 560, 89),
('宋城墙', 2, 'https://example.com/images/songchengqiang.jpg', '[]', 50, 27.0, 62.3, '千年古城墙，历史见证', '肇庆宋城墙是中国现存最完整的宋代城墙之一，具有重要的历史价值。', '城墙周长2800米，保存完好，可以俯瞰肇庆老城区风貌。', '09:00-17:00', '容易', '市中心', 23.0475, 112.4650, '肇庆市端州区宋城路', 1, 420, 76);

-- 插入测试收藏数据
INSERT INTO user_favorites (user_id, target_type, target_id) VALUES
(1, 'scenic', 1),
(1, 'scenic', 2),
(2, 'scenic', 1),
(3, 'scenic', 3),
(4, 'scenic', 4);