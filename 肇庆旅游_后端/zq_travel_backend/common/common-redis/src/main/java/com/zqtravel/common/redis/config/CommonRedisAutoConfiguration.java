package com.zqtravel.common.redis.config;

import com.zqtravel.common.redis.cache.CacheKeyGenerator;
import com.zqtravel.common.redis.cache.RedisCacheAspect;
import com.zqtravel.common.redis.lock.RedisDistributedLock;
import com.zqtravel.common.redis.util.RateLimiter;
import com.zqtravel.common.redis.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@EnableConfigurationProperties(CommonRedisProperties.class)
@RequiredArgsConstructor
public class CommonRedisAutoConfiguration {

    private final CommonRedisProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public CacheKeyGenerator cacheKeyGenerator() {
        return new CacheKeyGenerator(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisCacheAspect redisCacheAspect(RedisTemplate<String, Object> redisTemplate,
                                              CacheKeyGenerator cacheKeyGenerator) {
        return new RedisCacheAspect(redisTemplate, properties, cacheKeyGenerator);
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisUtils redisUtils(RedisTemplate<String, Object> redisTemplate,
                                  StringRedisTemplate stringRedisTemplate) {
        return new RedisUtils(redisTemplate, stringRedisTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisDistributedLock redisDistributedLock(RedissonClient redissonClient) {
        return new RedisDistributedLock(redissonClient, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public RateLimiter rateLimiter(RedisTemplate<String, Object> redisTemplate) {
        return new RateLimiter(redisTemplate);
    }
}
