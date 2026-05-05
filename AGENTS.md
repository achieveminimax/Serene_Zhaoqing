# 肇庆旅游小程序开发规范 (AGENTS.md)

## 文档概述
本文档是肇庆旅游小程序后端系统的开发规范，基于系统架构设计文档和接口清单文档制定。旨在规范团队开发流程，保证代码质量，提高开发效率，确保系统可维护性和可扩展性。

## 1. 项目概述与技术栈

### 1.1 项目简介
肇庆旅游小程序是一个集景点推荐、音乐疗愈、健康管理、AI助手、电商购物于一体的综合性旅游服务平台。前端为微信小程序，后端采用微服务架构。

### 1.2 技术栈规范
- **后端框架**: Spring Boot 3.2.x + Spring Cloud Alibaba 2023.0.1.0
- **数据库**: MySQL 8.0.x + Redis 7.2.x
- **消息队列**: RabbitMQ 3.12.x
- **文件存储**: 腾讯云COS
- **AI集成**: MiniMax API
- **API网关**: Nginx 1.24.x + Spring Cloud Gateway
- **容器化**: Docker 24.x + Kubernetes 1.28.x
- **开发语言**: Java 17 (OpenJDK)

### 1.3 开发环境要求
- JDK 17+
- Maven 3.8+
- Docker & Docker Compose
- MySQL 8.0+
- Redis 7.0+
- RabbitMQ 3.12+
- Nacos 2.2+
- Sentinel Dashboard

## 2. 代码开发规范

### 2.1 项目结构规范
```
/Users/achieve/Documents/文稿 - AchiEVE的MacBook Air/Trae_Projects/肇庆旅游项目/肇庆旅游_后端/zq_travel_backend/
├── common/                    # 公共模块
│   ├── common-core/          # 核心工具类
│   ├── common-web/           # Web相关工具
│   └── common-redis/         # Redis工具
├── gateway/                  # API网关
├── services/                 # 微服务模块
│   ├── user-service/        # 用户服务
│   ├── scenic-service/      # 景点服务
│   ├── music-service/       # 音乐服务
│   ├── recipe-service/      # 食谱服务
│   ├── shop-service/        # 商店服务
│   ├── health-service/      # 健康服务
│   ├── ai-service/          # AI服务
│   ├── search-service/      # 搜索服务
│   └── file-service/        # 文件服务
├── config/                   # 配置中心配置
└── docs/                    # 项目文档
```

### 2.2 命名规范
#### 包命名
- 使用全小写，点分隔
- 格式：`com.zqtravel.模块名.子模块`
- 示例：`com.zqtravel.user.controller`

#### 类命名
- 使用大驼峰命名法（PascalCase）
- 接口以`I`开头（可选）或以`Service`、`Repository`等后缀
- 示例：`UserController`、`UserServiceImpl`、`UserRepository`

#### 方法命名
- 使用小驼峰命名法（camelCase）
- 动词开头，描述操作
- 示例：`getUserById`、`createOrder`、`updateProfile`

#### 变量命名
- 使用小驼峰命名法
- 有意义的英文单词
- 示例：`userName`、`orderList`、`totalCount`

#### 常量命名
- 使用全大写，下划线分隔
- 示例：`MAX_RETRY_COUNT`、`DEFAULT_PAGE_SIZE`

### 2.3 代码风格规范
#### 缩进与空格
- 使用4个空格缩进，禁止使用Tab
- 运算符前后加空格
- 方法参数逗号后加空格

#### 注释规范
- 类注释：说明类的作用、作者、创建时间
- 方法注释：说明方法功能、参数、返回值、异常
- 复杂逻辑注释：说明算法思路
- 使用JavaDoc格式

```java
/**
 * 用户服务接口
 * 
 * @author 开发团队
 * @since 2026-05-05
 */
public interface UserService {
    
    /**
     * 根据用户ID获取用户信息
     * 
     * @param userId 用户ID
     * @return 用户信息
     * @throws UserNotFoundException 用户不存在异常
     */
    UserDTO getUserById(Long userId) throws UserNotFoundException;
}
```

#### 异常处理
- 使用自定义异常，继承`RuntimeException`
- 异常信息清晰明确
- 记录异常日志
- 对外接口返回统一错误码

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

// 异常使用
public UserDTO getUserById(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BusinessException("USER_NOT_FOUND", "用户不存在"));
    return convertToDTO(user);
}
```

### 2.4 日志规范
#### 日志级别
- ERROR: 系统错误，需要立即处理
- WARN: 警告信息，需要注意
- INFO: 重要业务操作日志
- DEBUG: 调试信息，开发环境使用
- TRACE: 详细跟踪信息

#### 日志格式
```java
@Slf4j
@RestController
public class UserController {
    
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        log.info("查询用户信息，用户ID: {}", id);
        try {
            UserDTO user = userService.getUserById(id);
            log.debug("用户信息查询成功: {}", user);
            return ResponseEntity.ok(user);
        } catch (BusinessException e) {
            log.error("查询用户信息失败，用户ID: {}, 错误: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
```

## 3. API开发规范

### 3.1 RESTful API设计规范
#### URL设计
- 使用名词复数表示资源
- 使用HTTP方法表示操作
- 版本控制：`/api/v1/`
- 示例：`GET /api/v1/users`, `POST /api/v1/orders`

#### 请求方法规范
| 方法 | 用途 | 示例 |
|------|------|------|
| GET | 获取资源 | `GET /api/v1/users` |
| POST | 创建资源 | `POST /api/v1/users` |
| PUT | 更新资源（全量） | `PUT /api/v1/users/{id}` |
| PATCH | 更新资源（部分） | `PATCH /api/v1/users/{id}` |
| DELETE | 删除资源 | `DELETE /api/v1/users/{id}` |

#### 响应格式规范
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "name": "张三"
  },
  "timestamp": 1746451200
}
```

#### 错误响应格式
```json
{
  "code": 40001,
  "message": "参数验证失败",
  "errors": [
    {
      "field": "phone",
      "message": "手机号格式不正确"
    }
  ],
  "timestamp": 1746451200
}
```

### 3.2 接口版本管理
- 主版本在URL中：`/api/v1/`
- 向后兼容的修改不需要升级版本
- 不兼容的修改需要升级版本：`/api/v2/`
- 废弃的接口需要标注`@Deprecated`并说明替代接口

### 3.3 接口文档规范
- 使用Swagger/OpenAPI 3.0生成接口文档
- 每个接口必须有详细的描述
- 参数说明、返回值说明、错误码说明
- 示例请求和响应

```java
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "用户管理", description = "用户相关接口")
public class UserController {
    
    @Operation(summary = "获取用户列表", description = "分页查询用户列表")
    @GetMapping
    public ResponseEntity<PageResult<UserDTO>> getUsers(
            @Parameter(description = "页码", example = "1") 
            @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页大小", example = "20") 
            @RequestParam(defaultValue = "20") Integer size) {
        // 实现逻辑
    }
}
```

### 3.4 接口安全规范
#### 认证授权
- 使用JWT进行身份认证
- 接口权限控制（RBAC）
- 敏感操作需要二次验证

#### 参数校验
- 使用Spring Validation进行参数校验
- 防止SQL注入、XSS攻击
- 文件上传类型和大小限制

```java
@PostMapping("/register")
public ResponseEntity<UserDTO> register(
        @Valid @RequestBody RegisterRequest request) {
    // 自动验证参数
    return ResponseEntity.ok(userService.register(request));
}

// 请求对象
public class RegisterRequest {
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度6-20位")
    private String password;
    
    // getter/setter
}
```

## 4. 微服务开发规范

### 4.1 服务拆分原则
- 按业务领域拆分，高内聚低耦合
- 独立数据库，服务间通过API通信
- 服务间调用使用OpenFeign
- 事件驱动使用RabbitMQ

### 4.2 服务注册与发现
- 使用Nacos作为服务注册中心
- 服务名格式：`服务名-service`
- 服务端口：8001-8009

```yaml
# application.yml
spring:
  application:
    name: user-service
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
        namespace: ${spring.profiles.active}
        group: DEFAULT_GROUP
```

### 4.3 服务配置管理
- 使用Nacos作为配置中心
- 多环境配置：dev、test、prod
- 配置动态刷新

```yaml
# bootstrap.yml
spring:
  application:
    name: user-service
  profiles:
    active: dev
  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848
        file-extension: yaml
        namespace: ${spring.profiles.active}
        group: DEFAULT_GROUP
        refresh-enabled: true
```

### 4.4 服务间通信
#### HTTP调用（OpenFeign）
```java
@FeignClient(name = "order-service", path = "/api/v1/orders")
public interface OrderServiceClient {
    
    @GetMapping("/user/{userId}")
    List<OrderDTO> getOrdersByUserId(@PathVariable Long userId);
    
    @PostMapping
    OrderDTO createOrder(@RequestBody CreateOrderRequest request);
}
```

#### 消息队列（RabbitMQ）
```java
@Component
@RequiredArgsConstructor
public class OrderMessageProducer {
    
    private final RabbitTemplate rabbitTemplate;
    
    public void sendOrderCreatedEvent(OrderCreatedEvent event) {
        rabbitTemplate.convertAndSend(
            "order.exchange",
            "order.created",
            event
        );
    }
}

@Component
@Slf4j
public class OrderMessageConsumer {
    
    @RabbitListener(queues = "order.queue")
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("收到订单创建事件: {}", event);
        // 处理逻辑
    }
}
```

### 4.5 服务熔断与降级
- 使用Sentinel进行流量控制
- 配置熔断规则
- 降级策略

```java
@RestController
@Slf4j
public class UserController {
    
    @GetMapping("/users/{id}")
    @SentinelResource(value = "getUserById", blockHandler = "handleBlock")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        // 业务逻辑
    }
    
    // 熔断降级处理
    public ResponseEntity<UserDTO> handleBlock(Long id, BlockException ex) {
        log.warn("接口被限流，用户ID: {}", id);
        return ResponseEntity.status(429)
            .body(UserDTO.fallback(id));
    }
}
```

## 5. 数据库开发规范

### 5.1 数据库设计规范
#### 表命名
- 使用小写字母，下划线分隔
- 使用复数形式
- 示例：`users`、`scenic_spots`、`order_items`

#### 字段命名
- 使用小写字母，下划线分隔
- 有意义的英文单词
- 示例：`user_name`、`created_at`、`is_deleted`

#### 索引规范
- 主键使用`id`字段，BIGINT类型
- 外键字段添加索引
- 查询条件字段添加索引
- 联合索引注意字段顺序

```sql
-- 用户表示例
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    openid VARCHAR(128) UNIQUE COMMENT '微信OpenID',
    phone VARCHAR(20) COMMENT '手机号',
    nickname VARCHAR(100) COMMENT '昵称',
    avatar_url VARCHAR(500) COMMENT '头像URL',
    gender TINYINT COMMENT '性别 0-未知 1-男 2-女',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    is_deleted TINYINT DEFAULT 0 COMMENT '是否删除 0-否 1-是',
    
    INDEX idx_openid (openid),
    INDEX idx_phone (phone),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
```

### 5.2 数据访问规范
#### 使用MyBatis Plus
- 避免手写SQL，使用MyBatis Plus提供的CRUD接口
- 复杂查询使用QueryWrapper
- 分页查询使用Page对象

```java
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserMapper userMapper;
    
    @Override
    public PageResult<UserDTO> getUsers(Integer page, Integer size) {
        Page<User> pageParam = new Page<>(page, size);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", 0)
                   .orderByDesc("created_at");
        
        Page<User> userPage = userMapper.selectPage(pageParam, queryWrapper);
        
        return PageResult.of(
            userPage.getCurrent(),
            userPage.getSize(),
            userPage.getTotal(),
            userPage.getRecords().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList())
        );
    }
}
```

#### 事务管理
- 使用`@Transactional`注解
- 事务传播行为根据业务需求选择
- 只读事务使用`@Transactional(readOnly = true)`

```java
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    
    private final OrderMapper orderMapper;
    private final InventoryService inventoryService;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDTO createOrder(CreateOrderRequest request) {
        // 1. 扣减库存
        inventoryService.deductStock(request.getProductId(), request.getQuantity());
        
        // 2. 创建订单
        Order order = new Order();
        // 设置订单属性
        orderMapper.insert(order);
        
        // 3. 发送创建事件
        eventPublisher.publishEvent(new OrderCreatedEvent(order));
        
        return convertToDTO(order);
    }
}
```

### 5.3 缓存规范
#### Redis使用规范
- 缓存Key统一前缀：`zq:模块:业务:ID`
- 设置合理的过期时间
- 缓存穿透、雪崩、击穿防护

```java
@Component
@RequiredArgsConstructor
public class UserCacheService {
    
    private final RedisTemplate<String, Object> redisTemplate;
    
    private static final String USER_KEY_PREFIX = "zq:user:info:";
    private static final Duration USER_CACHE_TTL = Duration.ofHours(2);
    
    public UserDTO getUserFromCache(Long userId) {
        String key = USER_KEY_PREFIX + userId;
        UserDTO user = (UserDTO) redisTemplate.opsForValue().get(key);
        if (user != null) {
            return user;
        }
        
        // 数据库查询
        user = userService.getUserById(userId);
        if (user != null) {
            redisTemplate.opsForValue().set(key, user, USER_CACHE_TTL);
        }
        
        return user;
    }
}
```

## 6. 测试规范

### 6.1 测试分层
#### 单元测试（Unit Test）
- 测试单个方法或类
- 使用JUnit 5 + Mockito
- 覆盖率要求：核心业务代码80%+

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserServiceImpl userService;
    
    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setName("测试用户");
        
        when(userRepository.findById(userId))
            .thenReturn(Optional.of(user));
        
        // When
        UserDTO result = userService.getUserById(userId);
        
        // Then
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("测试用户", result.getName());
        
        verify(userRepository).findById(userId);
    }
}
```

#### 集成测试（Integration Test）
- 测试多个组件集成
- 使用`@SpringBootTest`
- 测试数据库操作、API接口

```java
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void getUser_WhenUserExists_ShouldReturnUser() throws Exception {
        mockMvc.perform(get("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.name").exists());
    }
}
```

#### 端到端测试（E2E Test）
- 测试完整业务流程
- 使用TestContainers模拟依赖服务
- 自动化测试脚本

### 6.2 测试数据管理
- 使用测试数据库，与生产环境隔离
- 测试数据初始化使用Flyway或Liquibase
- 每个测试用例独立数据，互不干扰

### 6.3 性能测试
- 使用JMeter进行接口性能测试
- 关键接口响应时间要求：P95 < 200ms
- 并发用户数测试

## 7. 部署规范

### 7.1 环境划分
- **开发环境（dev）**: 开发人员本地环境
- **测试环境（test）**: 集成测试环境
- **预发布环境（staging）**: 模拟生产环境
- **生产环境（prod）**: 线上环境

### 7.2 容器化规范
#### Dockerfile规范
```dockerfile
# 多阶段构建
FROM openjdk:17-jdk-slim as builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jre-slim
WORKDIR /app

# 创建非root用户
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

# 复制jar包
COPY --from=builder /app/target/*.jar app.jar

# 健康检查
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# 启动命令
ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### Docker Compose开发环境
```yaml
version: '3.8'
services:
  nacos:
    image: nacos/nacos-server:2.2.0
    ports:
      - "8848:8848"
    environment:
      - MODE=standalone
    networks:
      - travel-network

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

  # 其他服务...
```

### 7.3 部署流程
1. **代码提交**: Git提交到对应分支
2. **CI/CD**: 自动构建、测试、打包
3. **镜像构建**: 构建Docker镜像并推送到镜像仓库
4. **部署**: Kubernetes部署新版本
5. **验证**: 健康检查、接口测试
6. **监控**: 监控告警、日志收集

### 7.4 监控告警
#### 应用监控
- Spring Boot Actuator暴露监控端点
- Prometheus收集指标
- Grafana展示监控面板

#### 日志收集
- ELK Stack收集和分析日志
- 结构化日志输出
- 关键业务操作日志

#### 告警规则
- 服务不可用告警
- 响应时间超过阈值告警
- 错误率升高告警
- 资源使用率过高告警

## 8. 团队协作规范

### 8.1 Git工作流
#### 分支管理
- `main`: 生产环境代码，保护分支
- `develop`: 开发分支，功能集成
- `feature/*`: 功能开发分支
- `release/*`: 发布分支
- `hotfix/*`: 热修复分支

#### 提交规范
```
<类型>(<范围>): <描述>

[可选正文]

[可选脚注]
```

**类型**:
- `feat`: 新功能
- `fix`: 修复bug
- `docs`: 文档更新
- `style`: 代码格式调整
- `refactor`: 代码重构
- `test`: 测试相关
- `chore`: 构建过程或辅助工具变动

**示例**:
```
feat(user): 添加用户注册功能

- 实现手机号注册
- 添加短信验证码
- 完善参数校验

Closes #123
```

### 8.2 代码审查
- 所有代码必须经过Code Review
- 使用GitLab/GitHub的Merge Request
- 审查要点：代码质量、安全性、性能、可维护性

### 8.3 文档管理
- 接口文档：Swagger自动生成
- 架构设计文档：维护更新
- 部署文档：详细部署步骤
- 故障处理文档：常见问题解决方案

## 9. 安全规范

### 9.1 数据安全
- 敏感数据加密存储（密码、手机号等）
- 数据传输使用HTTPS
- 数据脱敏处理
- 访问日志审计

### 9.2 接口安全
- 接口限流防刷
- SQL注入防护
- XSS攻击防护
- CSRF防护

### 9.3 依赖安全
- 定期更新依赖版本
- 使用安全扫描工具（如OWASP Dependency Check）
- 禁止使用有安全漏洞的依赖

## 10. 性能优化规范

### 10.1 数据库优化
- 合理设计索引
- 避免全表扫描
- 分页查询优化
- 读写分离

### 10.2 缓存优化
- 热点数据缓存
- 缓存更新策略
- 缓存穿透、雪崩、击穿防护

### 10.3 代码优化
- 避免大对象创建
- 使用连接池
- 异步处理耗时操作
- 批量操作减少IO

## 附录

### A. 常用命令
```bash
# 启动开发环境
docker-compose up -d

# 构建项目
mvn clean package -DskipTests

# 运行测试
mvn test

# 代码格式化
mvn spotless:apply

# 安全检查
mvn dependency-check:check
```

### B. 工具推荐
- **IDE**: IntelliJ IDEA (推荐) 或 VS Code
- **数据库工具**: DBeaver 或 Navicat
- **API测试**: Postman 或 Insomnia
- **监控工具**: Prometheus + Grafana
- **日志工具**: ELK Stack

### C. 参考文档
- [Spring Boot官方文档](https://spring.io/projects/spring-boot)
- [Spring Cloud Alibaba文档](https://github.com/alibaba/spring-cloud-alibaba)
- [MyBatis Plus文档](https://baomidou.com/)
- [腾讯云COS文档](https://cloud.tencent.com/document/product/436)

---
*文档版本: 1.0*
*最后更新: 2026-05-05*
*基于系统架构设计文档和接口清单文档制定*