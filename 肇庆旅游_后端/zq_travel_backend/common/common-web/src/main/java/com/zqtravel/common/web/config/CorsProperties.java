package com.zqtravel.common.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "common.web.cors")
public class CorsProperties {

    private boolean enabled = true;

    private List<String> allowedOrigins = List.of("*");

    private List<String> allowedMethods = List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");

    private List<String> allowedHeaders = List.of("*");

    private List<String> exposedHeaders = List.of("X-Request-Id");

    private boolean allowCredentials = false;

    private long maxAge = 3600L;
}
