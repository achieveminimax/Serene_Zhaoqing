package com.zqtravel.common.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "common.web")
public class CommonWebProperties {

	private boolean requestLoggingEnabled = true;

	private boolean requestIdEnabled = true;

	private CorsProperties cors = new CorsProperties();
}
