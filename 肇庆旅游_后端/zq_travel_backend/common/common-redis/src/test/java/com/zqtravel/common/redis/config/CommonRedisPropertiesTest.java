package com.zqtravel.common.redis.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class CommonRedisPropertiesTest {

    @Test
    @DisplayName("默认属性值应该正确")
    void defaultProperties_ShouldBeCorrect() {
        CommonRedisProperties properties = new CommonRedisProperties();

        assertEquals(Duration.ofHours(2), properties.getDefaultExpire());
        assertEquals(Duration.ofMinutes(30), properties.getNullValueExpire());
        assertTrue(properties.isCacheNullValue());
        assertEquals("zq", properties.getKeyPrefix());
        assertNotNull(properties.getLock());
    }

    @Test
    @DisplayName("锁属性默认值应该正确")
    void lockProperties_ShouldBeCorrect() {
        CommonRedisProperties.LockProperties lock = new CommonRedisProperties.LockProperties();

        assertEquals(Duration.ofSeconds(3), lock.getWaitTime());
        assertEquals(Duration.ofSeconds(30), lock.getLeaseTime());
        assertEquals(3, lock.getRetryTimes());
        assertEquals(100L, lock.getRetryInterval());
    }

    @Test
    @DisplayName("属性应该可以修改")
    void properties_ShouldBeModifiable() {
        CommonRedisProperties properties = new CommonRedisProperties();
        properties.setDefaultExpire(Duration.ofHours(4));
        properties.setCacheNullValue(false);
        properties.setKeyPrefix("test");

        assertEquals(Duration.ofHours(4), properties.getDefaultExpire());
        assertFalse(properties.isCacheNullValue());
        assertEquals("test", properties.getKeyPrefix());
    }
}
