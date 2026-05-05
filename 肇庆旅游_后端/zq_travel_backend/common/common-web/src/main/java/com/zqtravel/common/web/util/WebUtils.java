package com.zqtravel.common.web.util;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

public class WebUtils {

    private static final String UNKNOWN = "unknown";
    private static final String LOCALHOST_IPV4 = "127.0.0.1";
    private static final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

    private WebUtils() {
    }

    public static String getClientIp(HttpServletRequest request) {
        if (request == null) {
            return UNKNOWN;
        }
        String ip = request.getHeader("X-Forwarded-For");
        if (isValidIp(ip)) {
            int index = ip.indexOf(',');
            return index != -1 ? ip.substring(0, index) : ip;
        }
        ip = request.getHeader("X-Real-IP");
        if (isValidIp(ip)) {
            return ip;
        }
        ip = request.getHeader("Proxy-Client-IP");
        if (isValidIp(ip)) {
            return ip;
        }
        ip = request.getHeader("WL-Proxy-Client-IP");
        if (isValidIp(ip)) {
            return ip;
        }
        ip = request.getHeader("HTTP_CLIENT_IP");
        if (isValidIp(ip)) {
            return ip;
        }
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (isValidIp(ip)) {
            return ip;
        }
        ip = request.getRemoteAddr();
        if (LOCALHOST_IPV6.equals(ip)) {
            ip = LOCALHOST_IPV4;
        }
        return ip;
    }

    public static String getUserAgent(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        return request.getHeader("User-Agent");
    }

    public static String getFullUrl(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String queryString = request.getQueryString();
        if (StringUtils.isNotBlank(queryString)) {
            return request.getRequestURL().append("?").append(queryString).toString();
        }
        return request.getRequestURL().toString();
    }

    private static boolean isValidIp(String ip) {
        return StringUtils.isNotBlank(ip) && !UNKNOWN.equalsIgnoreCase(ip);
    }
}
