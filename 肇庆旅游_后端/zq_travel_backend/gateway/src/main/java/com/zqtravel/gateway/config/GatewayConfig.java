package com.zqtravel.gateway.config;

import com.zqtravel.gateway.filter.GatewayAuthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GatewayAuthProperties.class)
public class GatewayConfig {
}
