#!/bin/bash
# ============================================
# 肇庆旅游小程序 - Docker 本地构建脚本
# ============================================

set -e

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# 默认配置
REGISTRY="zqtravel"
TAG="latest"
MODULE=""

# 显示帮助信息
show_help() {
    echo -e "${BLUE}肇庆旅游小程序 - Docker 构建脚本${NC}"
    echo ""
    echo "用法: ./docker-build.sh [选项] <模块名>"
    echo ""
    echo "选项:"
    echo "  -t, --tag <tag>       镜像标签 (默认: latest)"
    echo "  -r, --registry <url>  镜像仓库地址 (默认: zqtravel)"
    echo "  -h, --help            显示帮助信息"
    echo ""
    echo "示例:"
    echo "  ./docker-build.sh gateway              # 构建 gateway 镜像"
    echo "  ./docker-build.sh -t v1.0.0 gateway    # 构建 gateway:v1.0.0 镜像"
    echo "  ./docker-build.sh -r registry.cn-hangzhou.aliyuncs.com/zqtravel gateway"
    echo ""
    echo "支持的模块:"
    echo "  gateway               API 网关服务"
    echo "  user-service          用户服务 (M2)"
    echo "  scenic-service        景点服务 (M2)"
    echo "  music-service         音乐服务 (M2)"
    echo "  recipe-service        食谱服务 (M2)"
    echo "  shop-service          商店服务 (M2)"
    echo "  health-service        健康服务 (M2)"
    echo "  ai-service            AI 服务 (M2)"
    echo "  search-service        搜索服务 (M2)"
    echo "  file-service          文件服务 (M2)"
}

# 打印信息
log_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

log_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

log_step() {
    echo -e "${BLUE}[STEP]${NC} $1"
}

# 解析参数
while [[ $# -gt 0 ]]; do
    case $1 in
        -t|--tag)
            TAG="$2"
            shift 2
            ;;
        -r|--registry)
            REGISTRY="$2"
            shift 2
            ;;
        -h|--help)
            show_help
            exit 0
            ;;
        -*)
            log_error "未知选项: $1"
            show_help
            exit 1
            ;;
        *)
            MODULE="$1"
            shift
            ;;
    esac
done

# 检查模块名
if [ -z "$MODULE" ]; then
    log_error "请指定要构建的模块名"
    show_help
    exit 1
fi

# 检查模块是否存在
check_module() {
    local module=$1
    
    if [ "$module" == "gateway" ]; then
        if [ ! -f "gateway/Dockerfile" ]; then
            log_error "gateway/Dockerfile 不存在"
            exit 1
        fi
        return 0
    fi
    
    if [ ! -f "services/$module/Dockerfile" ]; then
        # 尝试从模板生成
        if [ -f "services/Dockerfile.template" ]; then
            log_warn "services/$module/Dockerfile 不存在，尝试从模板生成..."
            
            # 确定端口
            local port=""
            case $module in
                user-service) port=8001 ;;
                scenic-service) port=8002 ;;
                music-service) port=8003 ;;
                recipe-service) port=8004 ;;
                shop-service) port=8005 ;;
                health-service) port=8006 ;;
                ai-service) port=8007 ;;
                search-service) port=8008 ;;
                file-service) port=8009 ;;
                *)
                    log_error "未知服务: $module，无法确定端口号"
                    exit 1
                    ;;
            esac
            
            # 创建目录并生成 Dockerfile
            mkdir -p "services/$module"
            sed -e "s/{SERVICE_NAME}/$module/g" \
                -e "s/{PORT}/$port/g" \
                "services/Dockerfile.template" > "services/$module/Dockerfile"
            log_info "已生成 services/$module/Dockerfile (端口: $port)"
        else
            log_error "services/Dockerfile.template 模板不存在"
            exit 1
        fi
    fi
}

# 构建镜像
build_image() {
    local module=$1
    local tag=$2
    local registry=$3
    
    local image_name="$registry/$module:$tag"
    local dockerfile_path=""
    
    if [ "$module" == "gateway" ]; then
        dockerfile_path="gateway/Dockerfile"
    else
        dockerfile_path="services/$module/Dockerfile"
    fi
    
    log_step "开始构建镜像: $image_name"
    log_info "Dockerfile: $dockerfile_path"
    
    # 执行构建
    docker build \
        -f "$dockerfile_path" \
        -t "$image_name" \
        --build-arg MODULE="$module" \
        .
    
    if [ $? -eq 0 ]; then
        log_info "镜像构建成功: $image_name"
        echo ""
        log_info "运行镜像:"
        echo "  docker run -p <端口>:<端口> $image_name"
        echo ""
        log_info "推送镜像:"
        echo "  docker push $image_name"
    else
        log_error "镜像构建失败"
        exit 1
    fi
}

# 主流程
main() {
    log_step "===== 肇庆旅游小程序 Docker 构建 ====="
    log_info "模块: $MODULE"
    log_info "标签: $TAG"
    log_info "仓库: $REGISTRY"
    echo ""
    
    # 检查 Docker 环境
    if ! command -v docker &> /dev/null; then
        log_error "Docker 未安装或未在 PATH 中"
        exit 1
    fi
    
    # 检查是否在项目根目录
    if [ ! -f "pom.xml" ]; then
        log_error "请在项目根目录 (zq_travel_backend) 下执行此脚本"
        exit 1
    fi
    
    # 检查模块
    check_module "$MODULE"
    
    # 构建镜像
    build_image "$MODULE" "$TAG" "$REGISTRY"
    
    echo ""
    log_step "===== 构建完成 ====="
}

# 执行主流程
main
