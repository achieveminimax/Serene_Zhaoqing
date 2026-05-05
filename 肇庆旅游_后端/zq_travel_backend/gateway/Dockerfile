# ============================================
# Gateway 服务 Dockerfile
# 多阶段构建：编译阶段 + 运行阶段
# ============================================

# --------------------------------------------
# 阶段 1：构建阶段
# --------------------------------------------
FROM maven:3.9-eclipse-temurin-17-alpine AS builder

# 设置工作目录
WORKDIR /build

# 先复制父工程 POM 和当前模块 POM，利用 Docker 缓存层
COPY pom.xml ./
COPY gateway/pom.xml ./gateway/
COPY common/pom.xml ./common/
COPY common/common-core/pom.xml ./common/common-core/
COPY common/common-web/pom.xml ./common/common-web/
COPY common/common-redis/pom.xml ./common/common-redis/

# 下载依赖（不编译源码，利用缓存）
RUN mvn dependency:go-offline -pl gateway -am -q

# 复制所有源码
COPY common ./common
COPY gateway ./gateway

# 打包构建（跳过测试）
RUN mvn clean package -pl gateway -am -DskipTests -q

# --------------------------------------------
# 阶段 2：运行阶段
# --------------------------------------------
FROM eclipse-temurin:17-jre-alpine

# 维护者信息
LABEL maintainer="devops@zqtravel.com"
LABEL description="肇庆旅游小程序 - API网关服务"
LABEL version="1.0.0"

# 设置工作目录
WORKDIR /app

# 安装必要的工具（用于健康检查）
RUN apk add --no-cache curl

# 创建非 root 用户运行应用
RUN addgroup -S spring && adduser -S spring -G spring

# 复制构建产物
COPY --from=builder /build/gateway/target/gateway-*.jar app.jar

# 修改文件所属用户
RUN chown -R spring:spring /app

# 切换到非 root 用户
USER spring:spring

# 暴露服务端口
EXPOSE 9000

# 健康检查
HEALTHCHECK --interval=30s --timeout=5s --start-period=60s --retries=3 \
    CMD curl -f http://localhost:9000/actuator/health || exit 1

# JVM 参数（可通过环境变量覆盖）
ENV JVM_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+UseG1GC -XX:+UseStringDeduplication"
ENV SPRING_OPTS=""

# 启动命令
ENTRYPOINT ["sh", "-c", "java $JVM_OPTS $SPRING_OPTS -jar app.jar"]
