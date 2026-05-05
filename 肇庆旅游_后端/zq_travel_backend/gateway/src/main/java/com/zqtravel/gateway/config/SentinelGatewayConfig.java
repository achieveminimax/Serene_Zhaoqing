package com.zqtravel.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zqtravel.common.core.model.ApiResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SentinelGatewayConfig {

    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        GatewayCallbackManager.setBlockHandler(new CustomBlockRequestHandler(objectMapper));
        log.info("Sentinel gateway block handler initialized");
    }

    @RequiredArgsConstructor
    public static class CustomBlockRequestHandler implements BlockRequestHandler {

        private final ObjectMapper objectMapper;

        @Override
        public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable t) {
            log.warn("Request blocked by Sentinel: path={}, reason={}",
                    exchange.getRequest().getURI().getPath(),
                    t.getMessage());

            ApiResponse<Object> apiResponse = ApiResponse.error("429", "请求过于频繁，请稍后再试");
            try {
                String body = objectMapper.writeValueAsString(apiResponse);
                return ServerResponse
                        .status(HttpStatus.TOO_MANY_REQUESTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(body));
            } catch (JsonProcessingException e) {
                log.error("Failed to serialize block response", e);
                return ServerResponse
                        .status(HttpStatus.TOO_MANY_REQUESTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue("{\"code\":\"429\",\"message\":\"请求过于频繁\"}"));
            }
        }
    }
}
