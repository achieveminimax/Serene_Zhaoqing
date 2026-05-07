package com.zqtravel.common.web.config;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * CorsProperties单元测试
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@DisplayName("CorsProperties单元测试")
class CorsPropertiesTest {

    @Test
    @DisplayName("默认属性值应该正确")
    void defaultProperties_ShouldBeCorrect() {
        CorsProperties cors = new CorsProperties();

        assertTrue(cors.isEnabled());
        assertEquals(List.of("*"), cors.getAllowedOrigins());
        assertEquals(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"), cors.getAllowedMethods());
        assertEquals(List.of("*"), cors.getAllowedHeaders());
        assertEquals(List.of("X-Request-Id"), cors.getExposedHeaders());
        assertFalse(cors.isAllowCredentials());
        assertEquals(3600L, cors.getMaxAge());
    }

    @Test
    @DisplayName("属性应该可以修改")
    void properties_ShouldBeModifiable() {
        CorsProperties cors = new CorsProperties();

        cors.setEnabled(false);
        assertFalse(cors.isEnabled());

        List<String> origins = Arrays.asList("https://example.com", "https://app.example.com");
        cors.setAllowedOrigins(origins);
        assertEquals(origins, cors.getAllowedOrigins());

        List<String> methods = Arrays.asList("GET", "POST");
        cors.setAllowedMethods(methods);
        assertEquals(methods, cors.getAllowedMethods());

        List<String> headers = Arrays.asList("Content-Type", "Authorization");
        cors.setAllowedHeaders(headers);
        assertEquals(headers, cors.getAllowedHeaders());

        List<String> exposedHeaders = Arrays.asList("X-Request-Id", "X-Total-Count");
        cors.setExposedHeaders(exposedHeaders);
        assertEquals(exposedHeaders, cors.getExposedHeaders());

        cors.setAllowCredentials(true);
        assertTrue(cors.isAllowCredentials());

        cors.setMaxAge(7200L);
        assertEquals(7200L, cors.getMaxAge());
    }
}
