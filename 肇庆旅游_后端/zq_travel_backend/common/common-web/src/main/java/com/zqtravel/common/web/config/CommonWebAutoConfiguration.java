package com.zqtravel.common.web.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zqtravel.common.web.interceptor.RequestIdInterceptor;
import com.zqtravel.common.web.interceptor.RequestLoggingInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableConfigurationProperties(CommonWebProperties.class)
@RequiredArgsConstructor
public class CommonWebAutoConfiguration implements WebMvcConfigurer {

	private final CommonWebProperties properties;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if (properties.isRequestIdEnabled()) {
			registry.addInterceptor(requestIdInterceptor())
					.addPathPatterns("/**")
					.order(Integer.MIN_VALUE);
		}
		if (properties.isRequestLoggingEnabled()) {
			registry.addInterceptor(requestLoggingInterceptor())
					.addPathPatterns("/**")
					.order(Integer.MIN_VALUE + 1);
		}
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(
			prefix = "common.web",
			name = "request-id-enabled",
			havingValue = "true",
			matchIfMissing = true)
	public RequestIdInterceptor requestIdInterceptor() {
		return new RequestIdInterceptor();
	}

	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnProperty(
			prefix = "common.web",
			name = "request-logging-enabled",
			havingValue = "true",
			matchIfMissing = true)
	public RequestLoggingInterceptor requestLoggingInterceptor() {
		return new RequestLoggingInterceptor();
	}
}
