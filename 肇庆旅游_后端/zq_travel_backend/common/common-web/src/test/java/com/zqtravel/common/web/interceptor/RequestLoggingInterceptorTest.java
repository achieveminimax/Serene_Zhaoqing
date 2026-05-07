package com.zqtravel.common.web.interceptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

/**
 * RequestLoggingInterceptor单元测试
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@DisplayName("RequestLoggingInterceptor单元测试")
class RequestLoggingInterceptorTest {

    private RequestLoggingInterceptor interceptor;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        interceptor = new RequestLoggingInterceptor();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        MDC.clear();
    }

    @Test
    @DisplayName("preHandle: 应该设置开始时间属性")
    void preHandle_ShouldSetStartTime() {
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn("/api/test");
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        boolean result = interceptor.preHandle(request, response, new Object());

        assertTrue(result);
        verify(request).setAttribute(eq("requestStartTime"), anyLong());
    }

    @Test
    @DisplayName("afterCompletion: 应该计算并记录请求耗时")
    void afterCompletion_ShouldCalculateDuration() {
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn("/api/test");
        when(response.getStatus()).thenReturn(200);

        // 设置开始时间
        long startTime = System.currentTimeMillis() - 100; // 100ms前开始
        when(request.getAttribute("requestStartTime")).thenReturn(startTime);

        MDC.put("requestId", "test-request-id");

        assertDoesNotThrow(() -> interceptor.afterCompletion(request, response, new Object(), null));
    }

    @Test
    @DisplayName("afterCompletion: 没有开始时间时不应该抛出异常")
    void afterCompletion_NoStartTime_ShouldNotThrow() {
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn("/api/test");
        when(response.getStatus()).thenReturn(200);
        when(request.getAttribute("requestStartTime")).thenReturn(null);

        assertDoesNotThrow(() -> interceptor.afterCompletion(request, response, new Object(), null));
    }

    @Test
    @DisplayName("afterCompletion: 有异常时应该记录异常信息")
    void afterCompletion_WithException_ShouldLogException() {
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURI()).thenReturn("/api/test");
        when(response.getStatus()).thenReturn(500);

        long startTime = System.currentTimeMillis() - 100;
        when(request.getAttribute("requestStartTime")).thenReturn(startTime);

        Exception exception = new RuntimeException("test error");

        assertDoesNotThrow(() -> interceptor.afterCompletion(request, response, new Object(), exception));
    }
}
