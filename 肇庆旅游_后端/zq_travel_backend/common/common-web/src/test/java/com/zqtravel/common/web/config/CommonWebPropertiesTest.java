package com.zqtravel.common.web.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * CommonWebProperties单元测试
 *
 * @author 肇庆旅游开发团队
 * @since 2026-05-07
 */
@DisplayName("CommonWebProperties单元测试")
class CommonWebPropertiesTest {

    @Test
    @DisplayName("默认属性值应该正确")
    void defaultProperties_ShouldBeCorrect() {
        CommonWebProperties properties = new CommonWebProperties();

        assertTrue(properties.isRequestLoggingEnabled());
        assertTrue(properties.isRequestIdEnabled());
        assertNotNull(properties.getCors());
    }

    @Test
    @DisplayName("属性应该可以修改")
    void properties_ShouldBeModifiable() {
        CommonWebProperties properties = new CommonWebProperties();

        properties.setRequestLoggingEnabled(false);
        assertFalse(properties.isRequestLoggingEnabled());

        properties.setRequestIdEnabled(false);
        assertFalse(properties.isRequestIdEnabled());

        CorsProperties cors = new CorsProperties();
        cors.setEnabled(false);
        properties.setCors(cors);
        assertNotNull(properties.getCors());
        assertFalse(properties.getCors().isEnabled());
    }

    @Test
    @DisplayName("CorsProperties嵌套对象默认应该正确")
    void corsPropertiesDefault_ShouldBeCorrect() {
        CommonWebProperties properties = new CommonWebProperties();
        CorsProperties cors = properties.getCors();

        assertNotNull(cors);
        assertTrue(cors.isEnabled());
        assertNotNull(cors.getAllowedOrigins());
        assertNotNull(cors.getAllowedMethods());
        assertNotNull(cors.getAllowedHeaders());
        assertNotNull(cors.getExposedHeaders());
        assertFalse(cors.isAllowCredentials());
        assertEquals(3600L, cors.getMaxAge());
    }
}
