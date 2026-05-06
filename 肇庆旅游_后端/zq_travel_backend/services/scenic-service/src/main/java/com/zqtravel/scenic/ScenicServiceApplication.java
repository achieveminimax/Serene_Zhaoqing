package com.zqtravel.scenic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 景点服务启动类
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.zqtravel"})
@EnableFeignClients(basePackages = {"com.zqtravel"})
public class ScenicServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScenicServiceApplication.class, args);
    }
}