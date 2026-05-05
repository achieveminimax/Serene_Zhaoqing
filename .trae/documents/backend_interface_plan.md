# 肇庆旅游小程序后端功能接口规划文档

## 项目概述
基于现有微信小程序前端项目“疗愈生活”（肇庆旅游主题），规划后端系统开发。前端项目已包含多个功能模块：首页推荐、景点详情、AI助手、音乐播放、食谱推荐、健康数据、商店购物等。当前数据均为硬编码，需要开发后端系统提供动态数据管理和用户服务。

## 后端技术栈建议
- **后端框架**: Node.js + Express/Koa, 或 Python + Django/Flask/FastAPI, 或 Go + Gin
- **数据库**: MySQL/PostgreSQL (关系型) + Redis (缓存)
- **文件存储**: 对象存储 (如阿里云OSS、腾讯云COS) 用于图片、音频文件
- **AI集成**: MiniMax API 代理，或自建AI服务
- **部署**: Docker + Nginx, 云服务器 (如阿里云ECS、腾讯云CVM)
- **API风格**: RESTful API, JSON格式

## 数据库设计概要
### 核心数据表
1. **用户表 (users)**: 用户基本信息、设置
2. **景点表 (scenic_spots)**: 景点详情、图片、环境数据 (AQI、温度、湿度)
3. **音乐表 (musics)**: 音乐元数据、分类、音频文件URL
4. **食谱表 (recipes)**: 食谱详情、食材、步骤、营养信息
5. **商品表 (products)**: 商品信息、分类、价格、库存
6. **订单表 (orders)**: 订单信息、支付状态
7. **健康数据表 (health_data)**: 心率、步数、睡眠等
8. **收藏表 (favorites)**: 用户收藏的景点、音乐、食谱等
9. **搜索历史表 (search_history)**: 用户搜索记录
10. **AI对话历史表 (ai_conversations)**: AI对话记录
11. **播放列表表 (playlists)**: 用户创建的歌单
12. **系统配置表 (configs)**: 全局配置项

## 功能模块与API接口设计

### 1. 用户管理模块
#### 接口列表
- `POST /api/v1/auth/register` - 用户注册
- `POST /api/v1/auth/login` - 用户登录 (微信登录/手机号登录)
- `GET /api/v1/auth/profile` - 获取用户个人信息
- `PUT /api/v1/auth/profile` - 更新用户信息
- `POST /api/v1/auth/logout` - 用户登出
- `POST /api/v1/auth/refresh-token` - 刷新访问令牌

### 2. 首页数据模块
#### 接口列表
- `GET /api/v1/home/banners` - 获取轮播图列表
- `GET /api/v1/home/recommends` - 获取首页推荐列表
- `GET /api/v1/home/scenic-list` - 获取首页景点推荐列表
- `GET /api/v1/home/quick-actions` - 获取快捷操作入口

### 3. 景点管理模块
#### 接口列表
- `GET /api/v1/scenic/spots` - 获取景点列表 (支持分页、分类筛选)
- `GET /api/v1/scenic/spots/:id` - 获取景点详情
- `GET /api/v1/scenic/categories` - 获取景点分类
- `POST /api/v1/scenic/spots/:id/favorite` - 收藏/取消收藏景点
- `GET /api/v1/scenic/favorites` - 获取用户收藏的景点列表
- `GET /api/v1/scenic/nearby` - 获取附近景点 (基于地理位置)

### 4. AI助手模块
#### 接口列表
- `GET /api/v1/ai/agents` - 获取AI代理列表 (肇庆小助手、行程规划、心灵疗愈)
- `POST /api/v1/ai/chat` - 发送消息给AI代理
- `GET /api/v1/ai/conversations` - 获取用户对话历史
- `DELETE /api/v1/ai/conversations/:id` - 删除单条对话记录
- `DELETE /api/v1/ai/conversations` - 清空对话历史
- `GET /api/v1/ai/config` - 获取AI配置 (前端可配置代理)

### 5. 搜索模块
#### 接口列表
- `GET /api/v1/search` - 全局搜索 (景点、音乐、食谱、商品)
- `GET /api/v1/search/history` - 获取用户搜索历史
- `DELETE /api/v1/search/history/:id` - 删除单条搜索历史
- `DELETE /api/v1/search/history` - 清空搜索历史
- `GET /api/v1/search/trending` - 获取热门搜索词
- `GET /api/v1/search/suggestions` - 搜索建议 (自动补全)

### 6. 音乐模块
#### 接口列表
- `GET /api/v1/music/categories` - 获取音乐分类 (推荐、音乐、播客、冥想、助眠)
- `GET /api/v1/music/relax` - 获取放松音乐列表
- `GET /api/v1/music/nature-sounds` - 获取自然声音列表
- `GET /api/v1/music/meditation` - 获取冥想音乐列表
- `GET /api/v1/music/recommends` - 获取音乐推荐列表
- `POST /api/v1/music/:id/play` - 记录播放次数
- `POST /api/v1/music/:id/favorite` - 收藏/取消收藏音乐
- `GET /api/v1/music/favorites` - 获取用户收藏的音乐

### 7. 播放器模块
#### 接口列表
- `GET /api/v1/player/playlist` - 获取当前播放列表
- `POST /api/v1/player/playlist` - 创建播放列表
- `PUT /api/v1/player/playlist/:id` - 更新播放列表
- `DELETE /api/v1/player/playlist/:id` - 删除播放列表
- `POST /api/v1/player/playlist/:id/tracks` - 向播放列表添加歌曲
- `DELETE /api/v1/player/playlist/:id/tracks/:trackId` - 从播放列表移除歌曲
- `GET /api/v1/player/lyrics/:musicId` - 获取歌词

### 8. 食谱模块
#### 接口列表
- `GET /api/v1/recipes` - 获取食谱列表 (支持分页、分类)
- `GET /api/v1/recipes/:id` - 获取食谱详情
- `GET /api/v1/recipes/categories` - 获取食谱分类
- `POST /api/v1/recipes/:id/favorite` - 收藏/取消收藏食谱
- `GET /api/v1/recipes/favorites` - 获取用户收藏的食谱
- `POST /api/v1/recipes/:id/cooking-record` - 记录烹饪完成
- `GET /api/v1/recipes/daily-recommend` - 获取每日推荐食谱

### 9. 商店模块
#### 接口列表
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
#### 接口列表
- `POST /api/v1/health/heart-rate` - 上传心率数据
- `GET /api/v1/health/heart-rate` - 获取心率历史数据 (支持时间范围)
- `POST /api/v1/health/steps` - 上传步数数据
- `GET /api/v1/health/steps` - 获取步数历史数据 (支持日、周、月统计)
- `GET /api/v1/health/summary` - 获取健康数据汇总 (今日步数、平均心率、活跃时间等)
- `GET /api/v1/health/routes` - 获取推荐运动路线

### 11. 推荐详情模块
#### 接口列表
- `GET /api/v1/recommends` - 获取推荐内容列表 (编辑推荐)
- `GET /api/v1/recommends/:id` - 获取推荐详情
- `GET /api/v1/recommends/spotlight` - 获取今日焦点推荐

### 12. 收藏管理模块
#### 接口列表
- `GET /api/v1/favorites` - 获取用户所有收藏 (支持按类型筛选)
- `DELETE /api/v1/favorites/:id` - 删除单个收藏
- `GET /api/v1/favorites/counts` - 获取各类收藏数量统计

### 13. 系统配置模块
#### 接口列表
- `GET /api/v1/configs` - 获取系统配置 (前端配置项)
- `PUT /api/v1/configs/:key` - 更新配置项 (管理员)

### 14. 文件上传模块
#### 接口列表
- `POST /api/v1/upload/image` - 上传图片 (用户头像、反馈图片)
- `POST /api/v1/upload/audio` - 上传音频文件 (管理员)
- `GET /api/v1/upload/token` - 获取文件上传临时令牌 (用于直传对象存储)

## 数据模型定义 (示例)

### 景点模型 (ScenicSpot)
```json
{
  "id": "string",
  "name": "string",
  "category": "string",
  "heroImage": "string",
  "images": ["string"],
  "aqi": "number",
  "temperature": "number",
  "humidity": "number",
  "quote": "string",
  "description": "string",
  "descriptionSecondary": "string",
  "openTime": "string",
  "difficulty": "string",
  "distance": "string",
  "highlights": [
    {
      "image": "string",
      "title": "string",
      "description": "string"
    }
  ],
  "details": [
    {
      "title": "string",
      "items": ["string"]
    }
  ],
  "location": {
    "latitude": "number",
    "longitude": "number",
    "address": "string"
  },
  "createdAt": "datetime",
  "updatedAt": "datetime"
}
```

### 音乐模型 (Music)
```json
{
  "id": "string",
  "name": "string",
  "artist": "string",
  "emoji": "string",
  "tag": "string",
  "duration": "number",
  "image": "string",
  "audioUrl": "string",
  "lyrics": ["string"],
  "category": "string",
  "playCount": "number",
  "favoriteCount": "number",
  "createdAt": "datetime"
}
```

### 食谱模型 (Recipe)
```json
{
  "id": "string",
  "name": "string",
  "description": "string",
  "time": "string",
  "level": "string",
  "calories": "string",
  "servings": "string",
  "image": "string",
  "tags": ["string"],
  "benefits": [
    {
      "icon": "string",
      "title": "string",
      "desc": "string"
    }
  ],
  "ingredients": [
    {
      "name": "string",
      "amount": "string",
      "icon": "string"
    }
  ],
  "steps": [
    {
      "title": "string",
      "desc": "string"
    }
  ],
  "nutrition": {
    "protein": "string",
    "fat": "string",
    "carbs": "string",
    "fiber": "string"
  },
  "tips": "string",
  "createdAt": "datetime"
}
```

## 安全考虑
1. **身份验证**: JWT (JSON Web Token) 用于API访问控制
2. **权限控制**: 用户角色 (普通用户、管理员)
3. **数据验证**: 输入数据验证和清理
4. **API限流**: 防止滥用，限制请求频率
5. **HTTPS**: 所有API通信使用HTTPS加密
6. **敏感数据保护**: 用户密码加密存储 (bcrypt)
7. **SQL注入防护**: 使用参数化查询或ORM

## 部署建议
1. **开发环境**: 本地开发使用Docker Compose (数据库+Redis+后端)
2. **测试环境**: 独立测试服务器，模拟生产环境
3. **生产环境**: 
   - 云服务器 (2核4G以上)
   - 负载均衡 (如需高可用)
   - 数据库主从复制 (数据备份)
   - 监控告警 (应用性能、错误日志)
   - 日志收集 (ELK Stack或类似方案)

## 开发计划 (阶段划分)
### 第一阶段: 基础框架与核心模块 (2-3周)
- 项目初始化，技术栈搭建
- 数据库设计，模型定义
- 用户管理模块
- 景点管理模块
- 首页数据模块

### 第二阶段: 内容与功能模块 (3-4周)
- 音乐模块
- 食谱模块
- 搜索模块
- 收藏管理
- 文件上传

### 第三阶段: 高级功能与集成 (2-3周)
- AI助手模块 (集成MiniMax API)
- 健康数据模块
- 商店模块 (购物车、订单)
- 支付集成 (微信支付)

### 第四阶段: 优化与部署 (1-2周)
- 性能优化 (缓存、数据库索引)
- 安全加固
- 部署上线
- 监控与维护

## 下一步建议
1. **确定技术栈**: 根据团队技术背景选择后端语言和框架
2. **详细数据库设计**: 基于上述概要设计详细表结构
3. **API文档细化**: 使用Swagger/OpenAPI生成详细接口文档
4. **前端适配**: 修改前端代码，将硬编码数据替换为API调用
5. **开发环境搭建**: 配置开发、测试、生产环境
6. **持续集成/部署**: 建立CI/CD流程

---
*文档版本: 1.0*
*最后更新: 2026-05-05*
*基于前端项目扫描分析结果*