# 肇庆旅游小程序后端多模块结构设计文档

## 文档概述
本文档详细描述了肇庆旅游小程序后端系统的多模块结构设计，包括模块职责、依赖关系、开发规范和部署策略。基于《M1阶段里程碑文档》和《AGENTS.md开发规范》制定。

## 项目结构总览

### 目录结构
```
/Users/achieve/Documents/文稿 - AchiEVE的MacBook Air/Trae_Projects/肇庆旅游项目/肇庆旅游_后端/zq_travel_backend/
├── common/                    # 公共模块聚合工程
│   ├── common-core/          # 核心工具模块
│   ├── common-web/           # Web相关工具模块
│   └── common-redis/         # Redis工具模块
├── gateway/                  # API网关模块
├── services/                 # 微服务模块聚合工程
│   ├── user-service/        # 用户服务
│   ├── scenic-service/      # 景点服务
│   ├── music-service/       # 音乐服务
│   ├── recipe-service/      # 食谱服务
│   ├── shop-service/        # 商店服务
│   ├── health-service/      # 健康服务
│   ├── ai-service/          # AI服务
│   ├── search-service/      # 搜索服务
│   └── file-service/        # 文件服务
└── docs/                    # 项目文档
```

### Maven模块关系
```
zq-travel-parent (父工程)
├── common (公共模块聚合)
│   ├── common-core (核心工具)
│   ├── common-web (Web工具)
│   └── common-redis (Redis工具)
├── gateway (API网关)
└── services (微服务聚合)
    ├── user-service
    ├── scenic-service
    ├── music-service
    ├── recipe-service
    ├── shop-service
    ├── health-service
    ├── ai-service
    ├── search-service
    └── file-service
```

## 模块详细说明

### 1. 父工程 (zq-travel-parent)

#### 职责
- 统一依赖版本管理
- 统一构建配置
- 统一代码规范配置
- 定义子模块结构

#### 关键配置
- Spring Boot 3.2.5
- Spring Cloud Alibaba 2023.0.1.0
- Java 17
- 统一插件管理
- 统一属性配置

#### 依赖管理
- Spring Cloud Alibaba BOM
- Spring Cloud BOM
- 常用工具依赖版本统一管理

### 2. 公共模块 (common)

#### 2.1 common-core (核心工具模块)

##### 职责
- 统一响应格式封装 (`ApiResponse`)
- 统一异常处理 (`BusinessException`)
- 基础工具类 (`DateUtils`, `StringUtils`, `ValidationUtils`)
- 通用常量定义
- 基础数据模型

##### 关键特性
- 响应格式标准化
- 异常处理规范化
- 工具类丰富实用
- 支持Swagger文档注解

##### 依赖关系
- Spring Boot Starter
- Lombok (代码简化)
- Hutool (工具库)
- Apache Commons Lang3
- Jackson (JSON处理)
- JUnit 5 (测试)

#### 2.2 common-web (Web工具模块) - M2阶段开发

##### 计划职责
- 请求响应拦截器
- 参数验证工具
- 跨域配置
- Web安全工具
- 文件上传工具

#### 2.3 common-redis (Redis工具模块) - M2阶段开发

##### 计划职责
- Redis模板配置
- 缓存注解支持
- 分布式锁工具
- 缓存工具类
- 序列化配置

### 3. API网关 (gateway) - M2阶段开发

#### 计划职责
- 请求路由
- 认证授权
- 限流熔断
- 日志记录
- 请求转发

### 4. 微服务模块 (services) - M2阶段开发

#### 4.1 user-service (用户服务)
- 用户注册登录
- 用户信息管理
- 权限管理
- 会话管理

#### 4.2 scenic-service (景点服务)
- 景点信息管理
- 景点推荐
- 景点评价
- 景点搜索

#### 4.3 music-service (音乐服务)
- 音乐资源管理
- 音乐播放
- 音乐推荐
- 音乐分类

#### 4.4 recipe-service (食谱服务)
- 食谱管理
- 食材管理
- 烹饪步骤
- 营养分析

#### 4.5 shop-service (商店服务)
- 商品管理
- 订单管理
- 购物车
- 支付集成

#### 4.6 health-service (健康服务)
- 健康数据记录
- 健康分析
- 运动建议
- 饮食建议

#### 4.7 ai-service (AI服务)
- AI对话
- 智能推荐
- 图像识别
- 自然语言处理

#### 4.8 search-service (搜索服务)
- 全文搜索
- 搜索建议
- 搜索结果排序
- 搜索历史

#### 4.9 file-service (文件服务)
- 文件上传下载
- 文件存储
- 图片处理
- 文件管理

## 依赖关系设计

### 模块间依赖原则
1. **向下依赖原则**：上层模块可以依赖下层模块，下层模块不能依赖上层模块
2. **避免循环依赖**：模块间不允许出现循环依赖
3. **最小依赖原则**：只依赖必要的模块
4. **接口隔离原则**：通过接口进行模块间通信

### 具体依赖关系
```
common-core
  ↑
common-web (依赖common-core)
  ↑
common-redis (依赖common-core)
  ↑
gateway (依赖common-web, common-redis)
  ↑
services/* (依赖common-core, common-web, common-redis)
```

### 外部依赖管理
- **数据库**: MySQL 8.0 + MyBatis Plus
- **缓存**: Redis 7.2
- **消息队列**: RabbitMQ 3.12
- **服务注册发现**: Nacos 2.2
- **配置中心**: Nacos Config
- **流量控制**: Sentinel
- **API网关**: Spring Cloud Gateway
- **文件存储**: 腾讯云COS
- **AI服务**: MiniMax API

## 开发规范

### 代码结构规范
每个微服务模块遵循以下结构：
```
模块名/
├── src/main/java/com/zqtravel/模块名/
│   ├── controller/     # 控制器层
│   ├── service/        # 服务层
│   │   ├── impl/       # 服务实现
│   │   └── ...         # 服务接口
│   ├── mapper/         # 数据访问层
│   ├── model/          # 数据模型
│   │   ├── entity/     # 实体类
│   │   ├── dto/        # 数据传输对象
│   │   ├── vo/         # 视图对象
│   │   └── query/      # 查询对象
│   ├── config/         # 配置类
│   └── util/           # 工具类
├── src/main/resources/
│   ├── application.yml # 主配置文件
│   ├── application-{profile}.yml # 环境配置
│   └── mapper/         # MyBatis映射文件
└── src/test/java/      # 测试代码
```

### 包命名规范
- `com.zqtravel.模块名.controller` - 控制器
- `com.zqtravel.模块名.service` - 服务接口
- `com.zqtravel.模块名.service.impl` - 服务实现
- `com.zqtravel.模块名.mapper` - 数据访问
- `com.zqtravel.模块名.model.entity` - 实体类
- `com.zqtravel.模块名.model.dto` - 数据传输对象
- `com.zqtravel.模块名.config` - 配置类

### API设计规范
- RESTful风格
- 统一响应格式 (`ApiResponse`)
- 统一异常处理 (`BusinessException`)
- 版本控制 (`/api/v1/`)
- Swagger文档注解

## 构建与部署

### 构建命令
```bash
# 清理并编译所有模块
mvn clean compile

# 运行所有测试
mvn test

# 打包所有模块
mvn package -DskipTests

# 单独构建common-core模块
mvn clean compile -pl common/common-core

# 跳过测试构建
mvn clean package -DskipTests
```

### 多环境配置
- **开发环境 (dev)**: 本地开发环境
- **测试环境 (test)**: 集成测试环境
- **预发布环境 (staging)**: 模拟生产环境
- **生产环境 (prod)**: 线上环境

### Docker容器化
每个微服务独立容器化，通过Docker Compose编排：
```yaml
version: '3.8'
services:
  user-service:
    build: ./services/user-service
    ports:
      - "8001:8001"
    environment:
      - SPRING_PROFILES_ACTIVE=dev
    depends_on:
      - nacos
      - mysql
      - redis
  # 其他服务...
```

## 模块开发进度

### M1阶段 (已完成)
- [x] 父工程创建 (`zq-travel-parent`)
- [x] 公共模块聚合工程 (`common`)
- [x] 核心工具模块 (`common-core`)
  - [x] 统一响应格式 (`ApiResponse`)
  - [x] 统一异常处理 (`BusinessException`)
  - [x] 基础工具类 (`DateUtils`, `StringUtils`, `ValidationUtils`)
- [x] 多模块结构设计文档

### M2阶段 (计划中)
- [ ] Web工具模块 (`common-web`)
- [ ] Redis工具模块 (`common-redis`)
- [ ] API网关 (`gateway`)
- [ ] 微服务模块开发 (`services/*`)
- [ ] 数据库设计
- [ ] CI/CD流水线

## 注意事项

### 1. 模块新增流程
1. 在父工程`pom.xml`中添加模块声明
2. 创建模块目录结构
3. 配置模块`pom.xml`
4. 更新本文档
5. 验证模块构建

### 2. 依赖管理原则
1. 版本统一在父工程中管理
2. 模块间依赖使用`<dependency>`声明
3. 避免传递依赖冲突
4. 定期更新依赖版本

### 3. 代码合并规范
1. 功能开发使用`feature/*`分支
2. 代码合并前必须通过CI/CD检查
3. 必须进行Code Review
4. 遵循Git提交规范

### 4. 测试要求
1. 单元测试覆盖率 > 80%
2. 集成测试覆盖核心业务流程
3. 性能测试关键接口
4. 安全测试敏感接口

## 附录

### A. 常用命令参考
```bash
# 查看模块依赖树
mvn dependency:tree -pl common/common-core

# 检查依赖更新
mvn versions:display-dependency-updates

# 代码格式化
mvn spotless:apply

# 代码质量检查
mvn checkstyle:check

# 生成项目站点
mvn site
```

### B. 相关文档链接
- [AGENTS.md](../AGENTS.md) - 开发规范
- [M1阶段里程碑文档](../../../里程碑文档/M1_stage_milestone.md) - 阶段计划
- [系统架构设计文档](../../../system_architecture_design.md) - 架构设计
- [后端API接口清单](../../../backend_api_interfaces.md) - API设计

### C. 技术支持
- 架构师: 负责技术架构和框架设计
- DevOps工程师: 负责环境搭建和CI/CD
- 后端开发团队: 负责模块开发和维护

---
*文档版本: 1.0*
*创建日期: 2026-05-05*
*最后更新: 2026-05-05*
*维护团队: 肇庆旅游开发团队*