package com.zqtravel.recipe;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 食谱服务启动类
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.zqtravel.recipe.feign")
@MapperScan("com.zqtravel.recipe.repository")
@EnableScheduling
public class RecipeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipeServiceApplication.class, args);
    }
}