-- 测试数据：用户模块
-- 版本: V1.0.1
-- 描述: 插入测试用户数据

-- 插入测试用户
INSERT INTO users (openid, phone, nickname, avatar_url, gender, status, last_login_at) VALUES
('test_openid_001', '13800138001', '测试用户1', 'https://example.com/avatars/user1.jpg', 1, 1, NOW()),
('test_openid_002', '13800138002', '测试用户2', 'https://example.com/avatars/user2.jpg', 2, 1, NOW()),
('test_openid_003', '13800138003', '测试用户3', 'https://example.com/avatars/user3.jpg', 0, 1, NOW()),
('test_openid_004', '13800138004', '测试用户4', 'https://example.com/avatars/user4.jpg', 1, 0, NOW()), -- 禁用用户
('test_openid_005', '13800138005', '测试用户5', 'https://example.com/avatars/user5.jpg', 2, 1, NOW());

-- 插入用户认证数据
INSERT INTO user_auths (user_id, auth_type, auth_key, auth_secret) VALUES
(1, 'wechat', 'test_openid_001', 'encrypted_secret_001'),
(1, 'phone', '13800138001', '$2a$10$abcdefghijklmnopqrstuv'), -- 假设是BCrypt加密的密码
(2, 'wechat', 'test_openid_002', 'encrypted_secret_002'),
(3, 'wechat', 'test_openid_003', 'encrypted_secret_003'),
(4, 'phone', '13800138004', '$2a$10$abcdefghijklmnopqrstuv'),
(5, 'wechat', 'test_openid_005', 'encrypted_secret_005');