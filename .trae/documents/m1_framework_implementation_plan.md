# M1阶段基础框架实现计划

## 计划概述
本计划旨在实现《M1阶段里程碑文档》中第121-139行描述的基础框架父工程创建和公共模块开发任务。具体包括任务1.4（基础框架父工程创建）和任务2.1的部分内容（核心工具模块开发）。

## 实施范围
1. **父工程创建**：创建`zq-travel-parent`项目，配置Spring Boot 3.2.x和Spring Cloud Alibaba 2023.0.1.0
2. **多模块结构设计**：设计公共模块和微服务模块结构，配置模块依赖关系
3. **公共模块开发**：开发`common-core`模块，包括统一响应格式封装和统一异常处理

## 详细实施步骤

### 第1步：创建项目目录结构
根据里程碑文档中的项目结构规范，创建完整的后端项目目录结构：
```
/Users/achieve/Documents/文稿 - AchiEVE的MacBook Air/Trae_Projects/肇庆旅游项目/肇庆旅游_后端/zq_travel_backend/
├── common/
│   ├── common-core/
│   ├── common-web/
│   └── common-redis/
├── gateway/
├── services/
│   ├── user-service/
│   ├── scenic-service/
│   ├── music-service/
│   ├── recipe-service/
│   ├── shop-service/
│   ├── health-service/
│   ├── ai-service/
│   ├── search-service/
│   └── file-service/
└── docs/
```

### 第2步：创建父工程pom.xml
创建`zq-travel-parent`父工程的pom.xml文件，包含：
- Spring Boot 3.2.5父依赖
- Spring Cloud Alibaba 2023.0.1.0依赖管理
- 统一版本管理（Java 17、Lombok、MapStruct、MyBatis Plus等）
- 模块定义（common、gateway、services）

### 第3步：创建common-core模块
创建`common-core`模块的pom.xml和基础代码结构：
1. **统一响应格式封装**：创建`ApiResponse`类，包含成功和错误响应方法
2. **统一异常处理**：创建`BusinessException`自定义异常类
3. **工具类集合**：创建基础工具类（如日期处理、字符串处理等）

### 第4步：实现统一响应格式
根据AGENTS.md中的规范，实现`ApiResponse`类：
```java
public class ApiResponse<T> {
    private String code;
    private String message;
    private T data;
    private Long timestamp;
    
    // 成功响应静态方法
    public static <T> ApiResponse<T> success(T data) { ... }
    
    // 错误响应静态方法
    public static ApiResponse<Object> error(String code, String message) { ... }
}
```

### 第5步：实现统一异常处理
根据AGENTS.md中的规范，实现`BusinessException`类：
```java
public class BusinessException extends RuntimeException {
    private final String code;
    private final String message;
    
    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
```

### 第6步：创建基础工具类
创建常用工具类：
- `DateUtils`：日期时间处理工具
- `StringUtils`：字符串处理工具
- `ValidationUtils`：参数验证工具

### 第7步：配置单元测试
为`common-core`模块创建单元测试：
- `ApiResponseTest`：测试响应格式的正确性
- `BusinessExceptionTest`：测试异常类的功能
- 工具类单元测试

### 第8步：创建多模块结构设计文档
编写文档说明项目模块结构和依赖关系，包括：
- 模块职责说明
- 依赖关系图
- 开发规范说明

### 第9步：验证项目构建
运行Maven构建命令，确保所有模块能够正常编译和测试通过：
```bash
mvn clean compile -DskipTests
mvn test
```

## 技术规范要求
1. **代码规范**：遵循AGENTS.md中的命名规范、代码风格规范和注释规范
2. **API规范**：遵循RESTful API设计规范，使用统一响应格式
3. **异常处理**：使用自定义异常，提供清晰的错误信息
4. **日志规范**：使用SLF4J记录日志，遵循日志级别规范

## 交付物清单
1. `zq-travel-parent`父工程代码（pom.xml）
2. 统一依赖管理文件（pom.xml中的dependencyManagement）
3. 多模块结构设计文档（README.md或STRUCTURE.md）
4. `common-core`模块源代码
5. `common-core`模块单元测试
6. 公共模块API文档（通过JavaDoc生成）

## 风险评估与应对
1. **环境配置问题**：确保所有开发人员已安装JDK 17、Maven 3.8+和Docker
2. **依赖版本冲突**：使用BOM文件统一管理依赖版本
3. **团队协作问题**：提供详细的技术文档和示例代码

## 成功标准
1. 项目能够成功编译通过
2. 所有单元测试通过
3. 代码符合AGENTS.md中的开发规范
4. 模块结构清晰，依赖关系合理
5. 统一响应格式和异常处理能够正常工作

## 下一步计划
完成M1阶段基础框架后，可以继续开发：
1. `common-web`模块（请求响应拦截器、参数验证工具、跨域配置）
2. `common-redis`模块（Redis模板配置、缓存注解支持、分布式锁工具）
3. API网关基础配置
4. Nacos服务注册发现配置