package com.zqtravel.scenic.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Redis缓存配置类
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Configuration
@EnableCaching
public class RedisCacheConfig {
    // 使用common-redis模块的自动配置
    // 可以在这里添加自定义的缓存配置
}