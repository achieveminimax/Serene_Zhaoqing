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
 * RequestIdInterceptor单元测试
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@DisplayName("RequestIdInterceptor单元测试")
class RequestIdInterceptorTest {

    private RequestIdInterceptor interceptor;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        interceptor = new RequestIdInterceptor();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        MDC.clear();
    }

    @Test
    @DisplayName("preHandle: 请求头中无RequestId时应生成新的UUID")
    void preHandle_NoRequestId_ShouldGenerateNewId() {
        when(request.getHeader("X-Request-Id")).thenReturn(null);

        boolean result = interceptor.preHandle(request, response, new Object());

        assertTrue(result);
        verify(response).setHeader(eq("X-Request-Id"), anyString());
        assertNotNull(MDC.get("requestId"));
    }

    @Test
    @DisplayName("preHandle: 请求头中有RequestId时应使用现有值")
    void preHandle_WithRequestId_ShouldUseExistingId() {
        String existingId = "existing-request-id";
        when(request.getHeader("X-Request-Id")).thenReturn(existingId);

        boolean result = interceptor.preHandle(request, response, new Object());

        assertTrue(result);
        verify(response).setHeader("X-Request-Id", existingId);
        assertEquals(existingId, MDC.get("requestId"));
    }

    @Test
    @DisplayName("preHandle: 请求头中RequestId为空字符串时应生成新的UUID")
    void preHandle_EmptyRequestId_ShouldGenerateNewId() {
        when(request.getHeader("X-Request-Id")).thenReturn("");

        boolean result = interceptor.preHandle(request, response, new Object());

        assertTrue(result);
        verify(response).setHeader(eq("X-Request-Id"), anyString());
        assertNotNull(MDC.get("requestId"));
    }

    @Test
    @DisplayName("afterCompletion: 应该清理MDC中的requestId")
    void afterCompletion_ShouldClearMDC() {
        // 先设置MDC
        MDC.put("requestId", "test-request-id");
        assertNotNull(MDC.get("requestId"));

        interceptor.afterCompletion(request, response, new Object(), null);

        assertNull(MDC.get("requestId"));
    }

    @Test
    @DisplayName("afterCompletion: 有异常时也应该清理MDC")
    void afterCompletion_WithException_ShouldClearMDC() {
        MDC.put("requestId", "test-request-id");
        Exception exception = new RuntimeException("test exception");

        interceptor.afterCompletion(request, response, new Object(), exception);

        assertNull(MDC.get("requestId"));
    }
}
