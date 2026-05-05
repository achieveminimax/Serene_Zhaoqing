# 肇庆旅游小程序后端功能接口清单

## 概述
基于微信小程序前端项目"疗愈生活"（肇庆旅游主题）的后端API接口清单，共14个功能模块，总计约70个接口。

## 接口清单

### 1. 用户管理模块
- `POST /api/v1/auth/register` - 用户注册
- `POST /api/v1/auth/login` - 用户登录 (微信登录/手机号登录)
- `GET /api/v1/auth/profile` - 获取用户个人信息
- `PUT /api/v1/auth/profile` - 更新用户信息
- `POST /api/v1/auth/logout` - 用户登出
- `POST /api/v1/auth/refresh-token` - 刷新访问令牌

### 2. 首页数据模块
- `GET /api/v1/home/banners` - 获取轮播图列表
- `GET /api/v1/home/recommends` - 获取首页推荐列表
- `GET /api/v1/home/scenic-list` - 获取首页景点推荐列表
- `GET /api/v1/home/quick-actions` - 获取快捷操作入口

### 3. 景点管理模块
- `GET /api/v1/scenic/spots` - 获取景点列表 (支持分页、分类筛选)
- `GET /api/v1/scenic/spots/:id` - 获取景点详情
- `GET /api/v1/scenic/categories` - 获取景点分类
- `POST /api/v1/scenic/spots/:id/favorite` - 收藏/取消收藏景点
- `GET /api/v1/scenic/favorites` - 获取用户收藏的景点列表
- `GET /api/v1/scenic/nearby` - 获取附近景点 (基于地理位置)

### 4. AI助手模块
- `GET /api/v1/ai/agents` - 获取AI代理列表 (肇庆小助手、行程规划、心灵疗愈)
- `POST /api/v1/ai/chat` - 发送消息给AI代理
- `GET /api/v1/ai/conversations` - 获取用户对话历史
- `DELETE /api/v1/ai/conversations/:id` - 删除单条对话记录
- `DELETE /api/v1/ai/conversations` - 清空对话历史
- `GET /api/v1/ai/config` - 获取AI配置 (前端可配置代理)

### 5. 搜索模块
- `GET /api/v1/search` - 全局搜索 (景点、音乐、食谱、商品)
- `GET /api/v1/search/history` - 获取用户搜索历史
- `DELETE /api/v1/search/history/:id` - 删除单条搜索历史
- `DELETE /api/v1/search/history` - 清空搜索历史
- `GET /api/v1/search/trending` - 获取热门搜索词
- `GET /api/v1/search/suggestions` - 搜索建议 (自动补全)

### 6. 音乐模块
- `GET /api/v1/music/categories` - 获取音乐分类 (推荐、音乐、播客、冥想、助眠)
- `GET /api/v1/music/relax` - 获取放松音乐列表
- `GET /api/v1/music/nature-sounds` - 获取自然声音列表
- `GET /api/v1/music/meditation` - 获取冥想音乐列表
- `GET /api/v1/music/recommends` - 获取音乐推荐列表
- `POST /api/v1/music/:id/play` - 记录播放次数
- `POST /api/v1/music/:id/favorite` - 收藏/取消收藏音乐
- `GET /api/v1/music/favorites` - 获取用户收藏的音乐

### 7. 播放器模块
- `GET /api/v1/player/playlist` - 获取当前播放列表
- `POST /api/v1/player/playlist` - 创建播放列表
- `PUT /api/v1/player/playlist/:id` - 更新播放列表
- `DELETE /api/v1/player/playlist/:id` - 删除播放列表
- `POST /api/v1/player/playlist/:id/tracks` - 向播放列表添加歌曲
- `DELETE /api/v1/player/playlist/:id/tracks/:trackId` - 从播放列表移除歌曲
- `GET /api/v1/player/lyrics/:musicId` - 获取歌词

### 8. 食谱模块
- `GET /api/v1/recipes` - 获取食谱列表 (支持分页、分类)
- `GET /api/v1/recipes/:id` - 获取食谱详情
- `GET /api/v1/recipes/categories` - 获取食谱分类
- `POST /api/v1/recipes/:id/favorite` - 收藏/取消收藏食谱
- `GET /api/v1/recipes/favorites` - 获取用户收藏的食谱
- `POST /api/v1/recipes/:id/cooking-record` - 记录烹饪完成
- `GET /api/v1/recipes/daily-recommend` - 获取每日推荐食谱

### 9. 商店模块
- `GET /api/v1/shop/products` - 获取商品列表 (支持分类、排序)
- `GET /api/v1/shop/products/:id` - 获取商品详情
- `GET /api/v1/shop/categories` - 获取商品分类
- `GET /api/v1/shop/cart` - 获取购物车内容
- `POST /api/v1/shop/cart` - 添加商品到购物车
- `PUT /api/v1/shop/cart/:itemId` - 更新购物车商品数量
- `DELETE /api/v1/shop/cart/:itemId` - 从购物车移除商品
- `POST /api/v1/shop/orders` - 创建订单
- `GET /api/v1/shop/orders` - 获取用户订单列表
- `GET /api/v1/shop/orders/:id` - 获取订单详情
- `POST /api/v1/shop/orders/:id/cancel` - 取消订单
- `POST /api/v1/shop/orders/:id/pay` - 支付订单 (模拟或集成微信支付)

### 10. 健康数据模块
- `POST /api/v1/health/heart-rate` - 上传心率数据
- `GET /api/v1/health/heart-rate` - 获取心率历史数据 (支持时间范围)
- `POST /api/v1/health/steps` - 上传步数数据
- `GET /api/v1/health/steps` - 获取步数历史数据 (支持日、周、月统计)
- `GET /api/v1/health/summary` - 获取健康数据汇总 (今日步数、平均心率、活跃时间等)
- `GET /api/v1/health/routes` - 获取推荐运动路线

### 11. 推荐详情模块
- `GET /api/v1/recommends` - 获取推荐内容列表 (编辑推荐)
- `GET /api/v1/recommends/:id` - 获取推荐详情
- `GET /api/v1/recommends/spotlight` - 获取今日焦点推荐

### 12. 收藏管理模块
- `GET /api/v1/favorites` - 获取用户所有收藏 (支持按类型筛选)
- `DELETE /api/v1/favorites/:id` - 删除单个收藏
- `GET /api/v1/favorites/counts` - 获取各类收藏数量统计

### 13. 系统配置模块
- `GET /api/v1/configs` - 获取系统配置 (前端配置项)
- `PUT /api/v1/configs/:key` - 更新配置项 (管理员)

### 14. 文件上传模块
- `POST /api/v1/upload/image` - 上传图片 (用户头像、反馈图片)
- `POST /api/v1/upload/audio` - 上传音频文件 (管理员)
- `GET /api/v1/upload/token` - 获取文件上传临时令牌 (用于直传对象存储)

## 接口统计
- 用户管理模块: 6个接口
- 首页数据模块: 4个接口
- 景点管理模块: 6个接口
- AI助手模块: 6个接口
- 搜索模块: 6个接口
- 音乐模块: 8个接口
- 播放器模块: 7个接口
- 食谱模块: 7个接口
- 商店模块: 12个接口
- 健康数据模块: 6个接口
- 推荐详情模块: 3个接口
- 收藏管理模块: 3个接口
- 系统配置模块: 2个接口
- 文件上传模块: 3个接口

**总计: 79个接口**

---
*文档版本: 1.0*
*最后更新: 2026-05-05*
*基于前端项目"疗愈生活"扫描分析*