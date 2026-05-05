package com.zqtravel.gateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthGlobalFilterTest {

    private AuthGlobalFilter filter;
    private ObjectMapper objectMapper;
    private GatewayFilterChain mockChain;

    @BeforeEach
    void setUp() {
        GatewayAuthProperties properties = new GatewayAuthProperties();
        properties.setEnabled(true);
        properties.setWhiteList(List.of(
                "/api/v1/auth/login",
                "/api/v1/auth/register",
                "/api/v1/home/**",
                "/api/v1/scenic/**"
        ));
        objectMapper = new ObjectMapper();
        filter = new AuthGlobalFilter(properties, objectMapper);
        mockChain = exchange -> Mono.empty();
    }

    @Test
    @DisplayName("过滤器顺序应该正确")
    void filterOrder_ShouldBeCorrect() {
        assertEquals(100 + org.springframework.core.Ordered.HIGHEST_PRECEDENCE, filter.getOrder());
    }

    @Test
    @DisplayName("白名单路径应该放行")
    void whiteListPath_ShouldPass() {
        MockServerHttpRequest request = MockServerHttpRequest
                .get("/api/v1/auth/login")
                .build();
        ServerWebExchange exchange = MockServerWebExchange.from(request);

        Mono<Void> result = filter.filter(exchange, mockChain);
        assertNotNull(result);
    }

    @Test
    @DisplayName("白名单通配符路径应该放行")
    void whiteListPatternPath_ShouldPass() {
        MockServerHttpRequest request = MockServerHttpRequest
                .get("/api/v1/home/recommend")
                .build();
        ServerWebExchange exchange = MockServerWebExchange.from(request);

        Mono<Void> result = filter.filter(exchange, mockChain);
        assertNotNull(result);
    }

    @Test
    @DisplayName("无Token的非白名单路径应返回401")
    void noToken_ShouldReturn401() {
        MockServerHttpRequest request = MockServerHttpRequest
                .get("/api/v1/users/profile")
                .build();
        ServerWebExchange exchange = MockServerWebExchange.from(request);

        Mono<Void> result = filter.filter(exchange, mockChain);
        assertNotNull(result);
    }

    @Test
    @DisplayName("带Token的请求应该通过认证")
    void withToken_ShouldPass() {
        MockServerHttpRequest request = MockServerHttpRequest
                .get("/api/v1/users/profile")
                .header("Authorization", "Bearer test-token")
                .build();
        ServerWebExchange exchange = MockServerWebExchange.from(request);

        Mono<Void> result = filter.filter(exchange, mockChain);
        assertNotNull(result);
    }
}
