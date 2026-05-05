package com.zqtravel.common.redis.cache;

import com.zqtravel.common.redis.config.CommonRedisProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class CacheKeyGeneratorTest {

    private CacheKeyGenerator keyGenerator;
    private CommonRedisProperties properties;

    @BeforeEach
    void setUp() {
        properties = new CommonRedisProperties();
        keyGenerator = new CacheKeyGenerator(properties);
    }

    @Test
    @DisplayName("使用固定key生成缓存键")
    void generate_WithFixedKey() throws Exception {
        Method method = getTestMethod();
        String result = keyGenerator.generate("user:info", "", method, new Object[]{1L});

        assertEquals("zq:user:info", result);
    }

    @Test
    @DisplayName("使用默认方式生成缓存键")
    void generate_WithDefaultKey() throws Exception {
        Method method = getTestMethod();
        String result = keyGenerator.generate("", "", method, new Object[]{1L});

        assertNotNull(result);
        assertTrue(result.startsWith("zq:"));
    }

    @Test
    @DisplayName("使用keyGenerator生成缓存键")
    void generate_WithKeyGenerator() throws Exception {
        Method method = getTestMethod();
        String result = keyGenerator.generate("", "customGenerator", method, new Object[]{1L});

        assertEquals("zq:customGenerator", result);
    }

    private Method getTestMethod() throws Exception {
        return this.getClass().getDeclaredMethod("dummyMethod", Long.class);
    }

    @SuppressWarnings("unused")
    public void dummyMethod(Long id) {
    }
}
