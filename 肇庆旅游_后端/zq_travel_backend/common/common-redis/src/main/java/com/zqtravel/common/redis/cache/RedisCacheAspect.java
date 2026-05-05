package com.zqtravel.common.redis.cache;

import com.zqtravel.common.redis.annotation.RedisCache;
import com.zqtravel.common.redis.annotation.RedisCacheEvict;
import com.zqtravel.common.redis.annotation.RedisCachePut;
import com.zqtravel.common.redis.config.CommonRedisProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class RedisCacheAspect {

    private final RedisTemplate<String, Object> redisTemplate;
    private final CommonRedisProperties properties;
    private final CacheKeyGenerator keyGenerator;

    @Around("@annotation(redisCache)")
    public Object aroundCache(ProceedingJoinPoint joinPoint, RedisCache redisCache) throws Throwable {
        String cacheKey = keyGenerator.generate(redisCache.key(), redisCache.keyGenerator(),
                getMethod(joinPoint), joinPoint.getArgs());

        try {
            Object cachedValue = redisTemplate.opsForValue().get(cacheKey);
            if (cachedValue != null) {
                log.debug("Cache hit: key={}", cacheKey);
                return cachedValue;
            }
            if (redisCache.cacheNull() && properties.isCacheNullValue()) {
                Object nullMarker = redisTemplate.opsForValue().get(cacheKey + ":null");
                if (nullMarker != null) {
                    log.debug("Null cache hit: key={}", cacheKey);
                    return null;
                }
            }
        } catch (Exception e) {
            log.warn("Cache read failed: key={}, error={}", cacheKey, e.getMessage());
        }

        Object result = joinPoint.proceed();

        try {
            long expire = redisCache.expire() > 0 ? redisCache.expire() : properties.getDefaultExpire().getSeconds();
            if (result != null) {
                redisTemplate.opsForValue().set(cacheKey, result, expire, redisCache.timeUnit());
                log.debug("Cache set: key={}, expire={}{}", cacheKey, expire, redisCache.timeUnit());
            } else if (redisCache.cacheNull() && properties.isCacheNullValue()) {
                redisTemplate.opsForValue().set(cacheKey + ":null", "NULL",
                        properties.getNullValueExpire().getSeconds(), TimeUnit.SECONDS);
                log.debug("Null cache set: key={}", cacheKey);
            }
        } catch (Exception e) {
            log.warn("Cache write failed: key={}, error={}", cacheKey, e.getMessage());
        }

        return result;
    }

    @Around("@annotation(redisCacheEvict)")
    public Object aroundEvict(ProceedingJoinPoint joinPoint, RedisCacheEvict redisCacheEvict) throws Throwable {
        Object result = joinPoint.proceed();

        try {
            if (redisCacheEvict.allEntries()) {
                String pattern = keyGenerator.generate(redisCacheEvict.key(), redisCacheEvict.keyGenerator(),
                        getMethod(joinPoint), joinPoint.getArgs()) + "*";
                java.util.Set<String> keys = redisTemplate.keys(pattern);
                if (keys != null && !keys.isEmpty()) {
                    redisTemplate.delete(keys);
                    log.debug("Cache evict all: pattern={}, count={}", pattern, keys.size());
                }
            } else {
                String cacheKey = keyGenerator.generate(redisCacheEvict.key(), redisCacheEvict.keyGenerator(),
                        getMethod(joinPoint), joinPoint.getArgs());
                redisTemplate.delete(cacheKey);
                redisTemplate.delete(cacheKey + ":null");
                log.debug("Cache evict: key={}", cacheKey);
            }
        } catch (Exception e) {
            log.warn("Cache evict failed: error={}", e.getMessage());
        }

        return result;
    }

    @Around("@annotation(redisCachePut)")
    public Object aroundPut(ProceedingJoinPoint joinPoint, RedisCachePut redisCachePut) throws Throwable {
        Object result = joinPoint.proceed();

        try {
            String cacheKey = keyGenerator.generate(redisCachePut.key(), redisCachePut.keyGenerator(),
                    getMethod(joinPoint), joinPoint.getArgs());
            long expire = redisCachePut.expire() > 0 ? redisCachePut.expire() : properties.getDefaultExpire().getSeconds();
            if (result != null) {
                redisTemplate.opsForValue().set(cacheKey, result, expire, redisCachePut.timeUnit());
                log.debug("Cache put: key={}, expire={}{}", cacheKey, expire, redisCachePut.timeUnit());
            }
        } catch (Exception e) {
            log.warn("Cache put failed: error={}", e.getMessage());
        }

        return result;
    }

    private Method getMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }
}
