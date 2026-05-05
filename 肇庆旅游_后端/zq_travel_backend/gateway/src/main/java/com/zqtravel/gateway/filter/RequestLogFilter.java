package com.zqtravel.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
public class RequestLogFilter implements GlobalFilter, Ordered {

    private static final String REQUEST_ID_HEADER = "X-Request-Id";
    private static final String START_TIME_KEY = "requestStartTime";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String existingRequestId = request.getHeaders().getFirst(REQUEST_ID_HEADER);
        String requestId = (existingRequestId != null && !existingRequestId.isEmpty())
                ? existingRequestId
                : UUID.randomUUID().toString().replace("-", "");

        MDC.put("requestId", requestId);
        exchange.getAttributes().put(START_TIME_KEY, System.currentTimeMillis());

        if (log.isInfoEnabled()) {
            log.info("Gateway Request: {} {} from {} requestId={}",
                    request.getMethod(),
                    request.getURI().getPath(),
                    request.getRemoteAddress(),
                    requestId);
        }

        ServerHttpRequest mutatedRequest = request.mutate()
                .header(REQUEST_ID_HEADER, requestId)
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build())
                .doFinally(signalType -> {
                    Long startTime = exchange.getAttribute(START_TIME_KEY);
                    long duration = startTime != null ? System.currentTimeMillis() - startTime : -1;
                    if (log.isInfoEnabled()) {
                        log.info("Gateway Response: {} {} status={} duration={}ms requestId={}",
                                request.getMethod(),
                                request.getURI().getPath(),
                                exchange.getResponse().getStatusCode(),
                                duration,
                                requestId);
                    }
                    MDC.remove("requestId");
                });
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
