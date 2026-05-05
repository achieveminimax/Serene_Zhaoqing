package com.zqtravel.gateway.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GatewayAuthPropertiesTest {

    @Test
    @DisplayName("默认属性值应该正确")
    void defaultProperties_ShouldBeCorrect() {
        GatewayAuthProperties properties = new GatewayAuthProperties();
        assertTrue(properties.isEnabled());
        assertNotNull(properties.getWhiteList());
        assertTrue(properties.getWhiteList().isEmpty());
    }

    @Test
    @DisplayName("属性应该可以修改")
    void properties_ShouldBeModifiable() {
        GatewayAuthProperties properties = new GatewayAuthProperties();
        properties.setEnabled(false);
        properties.setWhiteList(List.of("/api/v1/auth/**", "/api/v1/home/**"));

        assertFalse(properties.isEnabled());
        assertEquals(2, properties.getWhiteList().size());
        assertTrue(properties.getWhiteList().contains("/api/v1/auth/**"));
    }
}
