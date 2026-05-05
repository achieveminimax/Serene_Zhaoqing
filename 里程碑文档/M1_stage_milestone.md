# M1阶段：项目启动与基础框架搭建详细里程碑文档

## 文档概述
本文档是肇庆旅游小程序后端系统M1阶段（项目启动与基础框架搭建）的详细实施计划。基于《项目里程碑文档》、《系统架构设计文档》和《后端API接口清单》制定，为项目团队提供M1阶段的具体任务指导、技术配置和验收标准。

## 阶段基本信息
- **阶段名称**: M1 - 项目启动与基础框架搭建
- **时间范围**: 第1-2周（2026-05-06 至 2026-05-17）
- **阶段目标**: 完成项目初始化、技术环境搭建和基础框架开发
- **负责人**: 项目经理、架构师、DevOps工程师
- **参与人员**: 全体项目成员

## 项目背景与范围

### 项目概述
肇庆旅游小程序是一个集景点推荐、音乐疗愈、健康管理、AI助手、电商购物于一体的综合性旅游服务平台。前端为微信小程序，后端采用微服务架构。

### 项目范围
- **技术栈**: Java Spring Cloud Alibaba + MySQL + Redis + RabbitMQ + 腾讯云COS + MiniMax API
- **微服务数量**: 9个核心微服务
- **API接口数量**: 79个RESTful API接口
- **功能模块**: 14个功能模块

### 项目组织结构
| 角色 | 人数 | M1阶段主要职责 |
|------|------|---------------|
| 项目经理 | 1 | 项目管理、进度跟踪、沟通协调 |
| 架构师 | 1 | 技术架构设计、框架搭建、技术决策 |
| DevOps工程师 | 1 | 环境搭建、CI/CD配置、容器化部署 |
| 后端开发 | 4-6 | 参与技术培训、环境配置、代码规范学习 |

## M1阶段详细任务分解

### 第1周（2026-05-06 至 2026-05-10）

#### 任务1.1：项目启动与团队建设（第1天）
**负责人**: 项目经理
**参与人员**: 全体成员

**具体任务**:
1. 召开项目启动会议
   - 介绍项目背景、目标和范围
   - 明确团队组织结构和职责
   - 建立沟通机制和决策流程
2. 制定项目章程
   - 定义项目成功标准
   - 建立风险管理机制
   - 制定项目沟通计划
3. 团队技术培训准备
   - 准备Spring Cloud Alibaba培训材料
   - 准备微服务架构培训材料
   - 准备开发规范培训材料

**交付物**:
- 项目章程文档
- 项目启动会议纪要
- 团队通讯录和沟通群组

#### 任务1.2：开发环境统一配置（第2-3天）
**负责人**: DevOps工程师
**参与人员**: 全体开发人员

**具体任务**:
1. 开发工具统一
   - IntelliJ IDEA企业版配置
   - VS Code配置（前端开发）
   - Git客户端配置
2. Maven环境配置
   - Maven 3.8+安装和配置
   - 阿里云镜像配置
   - 项目统一依赖管理
3. Docker开发环境
   - Docker Desktop安装
   - Docker Compose配置
   - 容器网络配置

**交付物**:
- 开发环境配置文档
- Docker Compose开发环境文件
- Maven settings.xml统一配置

#### 任务1.3：代码仓库与分支策略建立（第4天）
**负责人**: 架构师
**参与人员**: DevOps工程师

**具体任务**:
1. GitLab仓库创建
   - 创建主仓库：`zq_travel_backend`
   - 创建子模块仓库结构
   - 配置仓库权限
2. 分支策略制定
   - Git Flow分支模型
   - 开发分支：`develop`
   - 功能分支：`feature/*`
   - 发布分支：`release/*`
   - 热修复分支：`hotfix/*`
3. 代码规范配置
   - Checkstyle配置
   - Spotless代码格式化
   - Git提交规范

**交付物**:
- GitLab仓库结构
- 分支策略文档
- 代码规范检查配置

#### 任务1.4：基础框架父工程创建（第5天）
**负责人**: 架构师
**参与人员**: 全体后端开发

**具体任务**:
1. Spring Boot父工程
   - 创建`zq-travel-parent`项目
   - 配置Spring Boot 3.2.x
   - 配置Spring Cloud Alibaba 2023.0.1.0
2. 公共依赖管理
   - 统一依赖版本管理
   - BOM文件配置
   - 插件统一管理
3. 多模块结构设计
   - 设计公共模块结构
   - 设计微服务模块结构
   - 配置模块依赖关系

**交付物**:
- `zq-travel-parent`父工程代码
- 统一依赖管理文件
- 多模块结构设计文档

### 第2周（2026-05-13 至 2026-05-17）

#### 任务2.1：公共模块开发（第6-7天）
**负责人**: 架构师
**参与人员**: 全体后端开发

**具体任务**:
1. 核心工具模块（common-core）
   - 统一响应格式封装
   - 统一异常处理
   - 工具类集合
2. Web相关模块（common-web）
   - 请求响应拦截器
   - 参数验证工具
   - 跨域配置
3. 缓存工具模块（common-redis）
   - Redis模板配置
   - 缓存注解支持
   - 分布式锁工具

**交付物**:
- 公共模块源代码
- 公共模块单元测试
- 公共模块API文档

#### 任务2.2：微服务基础设施配置（第8天）
**负责人**: 架构师
**参与人员**: DevOps工程师

**具体任务**:
1. Nacos服务注册发现
   - Nacos Server部署（开发环境）
   - 服务注册配置
   - 配置中心集成
2. Sentinel流量控制
   - Sentinel Dashboard部署
   - 限流规则配置
   - 熔断降级配置
3. API网关基础配置
   - Spring Cloud Gateway配置
   - 路由规则定义
   - 认证过滤器基础

**交付物**:
- Nacos配置中心配置
- Sentinel规则配置
- API网关基础代码

#### 任务2.3：CI/CD流水线配置（第9天）
**负责人**: DevOps工程师
**参与人员**: 架构师

**具体任务**:
1. GitLab CI/CD配置
   - `.gitlab-ci.yml`配置文件
   - 流水线阶段定义
   - 自动化构建脚本
2. 自动化测试配置
   - 单元测试自动化
   - 代码质量检查
   - 测试报告生成
3. Docker镜像构建
   - 多阶段Dockerfile
   - 镜像仓库配置
   - 自动推送配置

**交付物**:
- GitLab CI/CD配置文件
- Docker镜像构建脚本
- 自动化测试报告模板

#### 任务2.4：技术培训与环境验证（第10天）
**负责人**: 项目经理
**参与人员**: 全体成员

**具体任务**:
1. 技术培训
   - Spring Cloud Alibaba实战培训
   - 微服务开发规范培训
   - 容器化部署培训
2. 环境验证
   - 开发环境完整验证
   - 代码构建验证
   - 服务启动验证
3. 知识转移
   - 技术文档整理
   - 常见问题解答
   - 最佳实践分享

**交付物**:
- 技术培训材料
- 环境验证报告
- 知识库初始内容

## 技术环境详细配置

### 开发环境配置

#### Docker Compose开发环境
```yaml
# docker-compose.yml
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: root123
      MYSQL_DATABASE: zq_travel
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
    networks:
      - travel-network

  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"
    volumes:
      - redis_data:/data
    networks:
      - travel-network

  rabbitmq:
    image: rabbitmq:3-management
    ports:
      - "5672:5672"
      - "15672:15672"
    environment:
      RABBITMQ_DEFAULT_USER: admin
      RABBITMQ_DEFAULT_PASS: admin123
    networks:
      - travel-network

  nacos:
    image: nacos/nacos-server:v2.2.0
    ports:
      - "8848:8848"
    environment:
      MODE: standalone
      SPRING_DATASOURCE_PLATFORM: mysql
      MYSQL_SERVICE_HOST: mysql
      MYSQL_SERVICE_DB_NAME: nacos
      MYSQL_SERVICE_USER: root
      MYSQL_SERVICE_PASSWORD: root123
    depends_on:
      - mysql
    networks:
      - travel-network

  sentinel:
    image: bladex/sentinel-dashboard:1.8.6
    ports:
      - "8080:8080"
    networks:
      - travel-network

volumes:
  mysql_data:
  redis_data:

networks:
  travel-network:
    driver: bridge
```

#### Maven统一配置
```xml
<!-- pom.xml 父工程配置 -->
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.zqtravel</groupId>
    <artifactId>zq-travel-parent</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>pom</packaging>
    
    <name>zq-travel-parent</name>
    <description>肇庆旅游小程序后端父工程</description>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.5</version>
        <relativePath/>
    </parent>
    
    <properties>
        <java.version>17</java.version>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        
        <!-- Spring Cloud Alibaba 版本 -->
        <spring-cloud-alibaba.version>2023.0.1.0</spring-cloud-alibaba.version>
        <spring-cloud.version>2023.0.0</spring-cloud.version>
        
        <!-- 工具版本 -->
        <lombok.version>1.18.30</lombok.version>
        <mapstruct.version>1.5.5.Final</mapstruct.version>
        <mybatis-plus.version>3.5.5</mybatis-plus.version>
    </properties>
    
    <dependencyManagement>
        <dependencies>
            <!-- Spring Cloud Alibaba 依赖管理 -->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>${spring-cloud-alibaba.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            
            <!-- Spring Cloud 依赖管理 -->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    
    <modules>
        <module>common</module>
        <module>gateway</module>
        <module>services</module>
    </modules>
</project>
```

### 项目结构规范
```
/Users/achieve/Documents/文稿 - AchiEVE的MacBook Air/Trae_Projects/肇庆旅游项目/肇庆旅游_后端/zq_travel_backend/
├── common/                    # 公共模块
│   ├── common-core/          # 核心工具类
│   ├── common-web/           # Web相关工具
│   └── common-redis/         # Redis工具
├── gateway/                  # API网关
├── services/                 # 微服务模块（M2阶段开发）
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

### Nacos客户端配置示例
```yaml
# application.yml (用户服务示例)
spring:
  application:
    name: user-service
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
        namespace: dev
        group: DEFAULT_GROUP
      config:
        server-addr: 127.0.0.1:8848
        file-extension: yaml
        namespace: dev
        group: DEFAULT_GROUP
        refresh-enabled: true

server:
  port: 8001

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
```

### Sentinel配置示例
```yaml
# application-sentinel.yml
spring:
  cloud:
    sentinel:
      transport:
        dashboard: 127.0.0.1:8080
        port: 8719
      eager: true
      datasource:
        ds1:
          nacos:
            server-addr: 127.0.0.1:8848
            dataId: ${spring.application.name}-sentinel
            groupId: DEFAULT_GROUP
            rule-type: flow
```

## 代码开发规范（M1阶段重点）

### 命名规范
1. **包命名**: `com.zqtravel.模块名.子模块`（全小写）
2. **类命名**: 大驼峰命名法（PascalCase）
3. **方法命名**: 小驼峰命名法（camelCase），动词开头
4. **变量命名**: 小驼峰命名法，有意义的英文单词
5. **常量命名**: 全大写，下划线分隔

### 代码风格规范
1. **缩进**: 4个空格，禁止使用Tab
2. **注释规范**:
   - 类注释：说明类的作用、作者、创建时间
   - 方法注释：说明方法功能、参数、返回值、异常
   - 复杂逻辑注释：说明算法思路
   - 使用JavaDoc格式

3. **异常处理规范**:
```java
// 自定义异常
public class BusinessException extends RuntimeException {
    private final String code;
    private final String message;
    
    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}

// 统一响应格式
public class ApiResponse<T> {
    private String code;
    private String message;
    private T data;
    private Long timestamp;
    
    // 成功响应
    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode("200");
        response.setMessage("success");
        response.setData(data);
        response.setTimestamp(System.currentTimeMillis());
        return response;
    }
    
    // 错误响应
    public static ApiResponse<Object> error(String code, String message) {
        ApiResponse<Object> response = new ApiResponse<>();
        response.setCode(code);
        response.setMessage(message);
        response.setTimestamp(System.currentTimeMillis());
        return response;
    }
}
```

### 日志规范
1. **日志级别**:
   - ERROR: 系统错误，需要立即处理
   - WARN: 警告信息，需要注意
   - INFO: 重要业务操作日志
   - DEBUG: 调试信息，开发环境使用
   - TRACE: 详细跟踪信息

2. **日志使用示例**:
```java
@Slf4j
@RestController
public class BaseController {
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        log.info("健康检查请求");
        return ResponseEntity.ok("OK");
    }
}
```

## CI/CD流水线设计

### GitLab CI/CD配置
```yaml
# .gitlab-ci.yml
stages:
  - build
  - test
  - quality
  - package
  - deploy-dev

variables:
  MAVEN_OPTS: "-Dmaven.repo.local=.m2/repository"
  DOCKER_REGISTRY: "registry.gitlab.com/your-group/zq-travel"

cache:
  paths:
    - .m2/repository

build:
  stage: build
  image: maven:3.8-openjdk-17
  script:
    - mvn clean compile -DskipTests
  artifacts:
    paths:
      - target/
    expire_in: 1 week

test:
  stage: test
  image: maven:3.8-openjdk-17
  script:
    - mvn test
  artifacts:
    reports:
      junit:
        - target/surefire-reports/TEST-*.xml
    expire_in: 1 week

code-quality:
  stage: quality
  image: maven:3.8-openjdk-17
  script:
    - mvn checkstyle:check
    - mvn spotless:check
  allow_failure: true

package:
  stage: package
  image: maven:3.8-openjdk-17
  script:
    - mvn package -DskipTests
    - docker build -t $DOCKER_REGISTRY/zq-travel-parent:latest .
  artifacts:
    paths:
      - target/*.jar
    expire_in: 1 week

deploy-dev:
  stage: deploy-dev
  image: docker:latest
  services:
    - docker:dind
  script:
    - docker login -u $CI_REGISTRY_USER -p $CI_REGISTRY_PASSWORD $CI_REGISTRY
    - docker push $DOCKER_REGISTRY/zq-travel-parent:latest
  only:
    - develop
```

### Docker镜像构建
```dockerfile
# Dockerfile (多阶段构建)
# 第一阶段：构建阶段
FROM maven:3.8-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# 第二阶段：运行阶段
FROM openjdk:17-jdk-slim
WORKDIR /app

# 创建非root用户
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

# 复制构建结果
COPY --from=builder /app/target/*.jar app.jar

# 健康检查
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# 运行应用
ENTRYPOINT ["java", "-jar", "app.jar"]
```

## 验收标准与检查清单

### 验收标准
- [ ] **开发环境可正常启动和运行**
  - Docker Compose环境所有服务正常启动
  - 数据库连接测试通过
  - Redis连接测试通过
  - RabbitMQ管理界面可访问
  - Nacos控制台可访问
  - Sentinel Dashboard可访问

- [ ] **基础框架代码通过单元测试**
  - 公共模块单元测试覆盖率>80%
  - 所有单元测试通过
  - 代码规范检查通过
  - 代码质量扫描无严重问题

- [ ] **CI/CD流水线成功执行**
  - 代码提交触发CI/CD流水线
  - 构建阶段成功完成
  - 测试阶段成功完成
  - 代码质量检查通过
  - Docker镜像构建和推送成功

- [ ] **团队所有成员完成技术培训和环境配置**
  - 所有开发人员完成开发环境配置
  - 所有开发人员完成技术培训
  - 所有开发人员能够成功构建项目
  - 所有开发人员理解代码规范和开发流程

### 详细检查清单

#### 环境检查清单
- [ ] JDK 17安装和配置
- [ ] Maven 3.8+安装和配置
- [ ] Docker Desktop安装和运行
- [ ] Git客户端安装和配置
- [ ] IntelliJ IDEA安装和配置
- [ ] 开发环境网络连通性

#### 代码检查清单
- [ ] 父工程`zq-travel-parent`创建完成
- [ ] 公共模块`common-core`开发完成
- [ ] 公共模块`common-web`开发完成
- [ ] 公共模块`common-redis`开发完成
- [ ] API网关基础配置完成
- [ ] 统一响应格式实现
- [ ] 统一异常处理实现
- [ ] 日志框架配置完成
- [ ] 代码规范检查配置完成

#### 配置检查清单
- [ ] Nacos服务注册发现配置完成
- [ ] Sentinel流量控制配置完成
- [ ] Spring Cloud Gateway配置完成
- [ ] 数据库连接池配置完成
- [ ] Redis连接配置完成
- [ ] 多环境配置文件准备

#### 流程检查清单
- [ ] GitLab仓库创建和权限配置
- [ ] 分支策略文档编写完成
- [ ] CI/CD流水线配置完成
- [ ] 代码审查流程建立
- [ ] 项目文档结构建立
- [ ] 沟通机制建立

## 风险管理与应对措施

### M1阶段特定风险
| 风险描述 | 影响程度 | 发生概率 | 应对措施 |
|---------|---------|---------|---------|
| 开发环境配置不一致 | 中 | 高 | 提供标准化的Docker开发环境，编写详细的环境配置文档 |
| 技术选型理解不一致 | 高 | 中 | 组织统一的技术培训，提供技术文档和示例代码 |
| 团队协作流程不熟悉 | 中 | 高 | 制定详细的协作流程文档，进行流程演练 |
| 第三方服务依赖问题 | 低 | 中 | 提前准备离线安装包，提供本地化部署方案 |

### 风险缓解策略
1. **环境一致性风险**
   - 使用Docker Compose统一开发环境
   - 提供环境配置检查脚本
   - 建立环境问题快速解决指南

2. **技术理解风险**
   - 组织Spring Cloud Alibaba专题培训
   - 提供代码示例和最佳实践
   - 建立技术问题答疑机制

3. **协作流程风险**
   - 制定详细的Git工作流文档
   - 进行代码审查流程演练
   - 建立问题跟踪和解决流程

## 沟通与报告机制

### M1阶段会议安排
| 会议类型 | 频率 | 时间 | 参与人员 | 主要内容 |
|---------|------|------|---------|---------|
| 项目启动会 | 1次 | 第1天上午 | 全体成员 | 项目介绍、团队组建、目标明确 |
| 技术培训会 | 3次 | 第1-2周 | 全体开发 | Spring Cloud、微服务、容器化培训 |
| 每日站会 | 每天 | 9:30-9:45 | 全体成员 | 进度同步、问题反馈、当日计划 |
| 周度总结会 | 1次 | 第2周周五 | 全体成员 | M1阶段总结、M2阶段规划 |

### 报告要求
1. **每日报告**: 每日站会后提交当日进展和问题
2. **周度报告**: 每周五提交M1阶段进展报告
3. **里程碑报告**: M1阶段结束后提交完整里程碑报告

## 交付物清单

### 文档类交付物
1. 项目章程文档
2. 开发环境配置文档
3. 代码规范文档
4. 分支策略文档
5. CI/CD流水线文档
6. 技术培训材料
7. M1阶段总结报告

### 代码类交付物
1. `zq-travel-parent`父工程代码
2. `common-core`公共模块代码
3. `common-web`公共模块代码
4. `common-redis`公共模块代码
5. API网关基础代码
6. 统一响应格式代码
7. 统一异常处理代码

### 配置类交付物
1. Docker Compose开发环境配置
2. Nacos配置中心配置
3. Sentinel规则配置
4. GitLab CI/CD配置
5. Maven统一配置
6. 代码规范检查配置

### 环境类交付物
1. 完整的Docker开发环境
2. GitLab代码仓库
3. 本地开发环境配置
4. 自动化构建环境

## 下一步计划（M2阶段准备）

### M2阶段概要
- **阶段名称**: M2 - 核心微服务开发
- **时间范围**: 第3-6周（2026-05-20 至 2026-06-14）
- **主要任务**: 数据库设计、核心微服务开发、基础API实现

### M1到M2过渡准备
1. **技术准备**
   - 数据库设计工具准备
   - MyBatis Plus框架学习
   - 数据库迁移工具学习

2. **人员准备**
   - 微服务开发任务分配
   - 数据库设计人员确定
   - 测试人员介入准备

3. **环境准备**
   - 测试数据库环境准备
   - 微服务监控环境准备
   - 接口测试工具准备

### 成功标准传递
M1阶段的成功标准将作为M2阶段的基础：
- 开发环境稳定性
- 代码规范一致性
- 团队协作流畅性
- 自动化流程可靠性

---
*文档版本: 1.0*
*创建日期: 2026-05-05*
*基于文档: project_milestones.md, system_architecture_design.md, backend_api_interfaces.md*
*下次评审: 2026-05-12*