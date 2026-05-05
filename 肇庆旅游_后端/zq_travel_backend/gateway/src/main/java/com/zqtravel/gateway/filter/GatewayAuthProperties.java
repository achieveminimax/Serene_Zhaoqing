package com.zqtravel.gateway.filter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "gateway.auth")
public class GatewayAuthProperties {

    private boolean enabled = true;

    private List<String> whiteList = new ArrayList<>();
}
