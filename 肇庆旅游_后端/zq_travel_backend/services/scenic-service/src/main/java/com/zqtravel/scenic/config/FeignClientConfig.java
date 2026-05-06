package com.zqtravel.scenic.config;

import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Feign客户端配置类
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Configuration
public class FeignClientConfig {

    /**
     * 配置Feign请求选项
     */
    @Bean
    public Request.Options feignOptions() {
        return new Request.Options(
            java.time.Duration.ofSeconds(10),   // 连接超时时间
            java.time.Duration.ofSeconds(30),   // 读取超时时间
            true                                // 是否跟随重定向
        );
    }
}