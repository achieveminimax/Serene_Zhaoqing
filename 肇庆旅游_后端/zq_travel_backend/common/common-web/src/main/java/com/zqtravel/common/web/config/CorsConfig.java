package com.zqtravel.common.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableConfigurationProperties(CorsProperties.class)
@RequiredArgsConstructor
@ConditionalOnProperty(
		prefix = "common.web.cors",
		name = "enabled",
		havingValue = "true",
		matchIfMissing = true)
public class CorsConfig {

	private final CorsProperties corsProperties;

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(corsProperties.getAllowedOrigins());
		config.setAllowedMethods(corsProperties.getAllowedMethods());
		config.setAllowedHeaders(corsProperties.getAllowedHeaders());
		config.setExposedHeaders(corsProperties.getExposedHeaders());
		config.setAllowCredentials(corsProperties.isAllowCredentials());
		config.setMaxAge(corsProperties.getMaxAge());

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
