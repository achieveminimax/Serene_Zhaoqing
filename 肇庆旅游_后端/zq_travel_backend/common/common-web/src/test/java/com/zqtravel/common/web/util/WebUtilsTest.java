package com.zqtravel.common.web.util;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebUtilsTest {

    @Test
    @DisplayName("从X-Forwarded-For头获取客户端IP")
    void getClientIp_FromXForwardedFor() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("X-Forwarded-For")).thenReturn("192.168.1.1, 10.0.0.1");
        when(request.getRemoteAddr()).thenReturn("10.0.0.1");

        String ip = WebUtils.getClientIp(request);
        assertEquals("192.168.1.1", ip);
    }

    @Test
    @DisplayName("从X-Real-IP头获取客户端IP")
    void getClientIp_FromXRealIP() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("X-Forwarded-For")).thenReturn(null);
        when(request.getHeader("X-Real-IP")).thenReturn("192.168.1.2");

        String ip = WebUtils.getClientIp(request);
        assertEquals("192.168.1.2", ip);
    }

    @Test
    @DisplayName("从RemoteAddr获取客户端IP")
    void getClientIp_FromRemoteAddr() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(anyString())).thenReturn(null);
        when(request.getRemoteAddr()).thenReturn("192.168.1.3");

        String ip = WebUtils.getClientIp(request);
        assertEquals("192.168.1.3", ip);
    }

    @Test
    @DisplayName("IPv6本地地址应转换为IPv4")
    void getClientIp_IPv6Localhost() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(anyString())).thenReturn(null);
        when(request.getRemoteAddr()).thenReturn("0:0:0:0:0:0:0:1");

        String ip = WebUtils.getClientIp(request);
        assertEquals("127.0.0.1", ip);
    }

    @Test
    @DisplayName("空请求应返回unknown")
    void getClientIp_NullRequest() {
        String ip = WebUtils.getClientIp(null);
        assertEquals("unknown", ip);
    }

    @Test
    @DisplayName("获取User-Agent")
    void getUserAgent() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("User-Agent")).thenReturn("Mozilla/5.0");

        String ua = WebUtils.getUserAgent(request);
        assertEquals("Mozilla/5.0", ua);
    }

    @Test
    @DisplayName("获取完整URL包含查询参数")
    void getFullUrl_WithQueryString() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost/api/users"));
        when(request.getQueryString()).thenReturn("page=1&size=10");

        String url = WebUtils.getFullUrl(request);
        assertEquals("http://localhost/api/users?page=1&size=10", url);
    }

    @Test
    @DisplayName("获取完整URL不含查询参数")
    void getFullUrl_WithoutQueryString() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost/api/users"));
        when(request.getQueryString()).thenReturn(null);

        String url = WebUtils.getFullUrl(request);
        assertEquals("http://localhost/api/users", url);
    }
}
