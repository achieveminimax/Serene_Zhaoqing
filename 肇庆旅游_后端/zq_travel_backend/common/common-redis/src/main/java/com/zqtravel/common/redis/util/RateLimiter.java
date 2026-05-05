package com.zqtravel.common.redis.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class RateLimiter {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String RATE_LIMIT_SCRIPT =
            "local current = redis.call('INCR', KEYS[1]) " +
            "if current == 1 then " +
            "  redis.call('EXPIRE', KEYS[1], ARGV[1]) " +
            "end " +
            "if current > tonumber(ARGV[2]) then " +
            "  return 0 " +
            "end " +
            "return 1";

    public boolean isAllowed(String key, int maxRequests, long windowSeconds) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>(RATE_LIMIT_SCRIPT, Long.class);
        Long result = redisTemplate.execute(script,
                Collections.singletonList(key),
                String.valueOf(windowSeconds),
                String.valueOf(maxRequests));
        return result != null && result == 1L;
    }

    public boolean isAllowed(String key, int maxRequests, long window, TimeUnit unit) {
        return isAllowed(key, maxRequests, unit.toSeconds(window));
    }

    public long getCurrentCount(String key) {
        Object count = redisTemplate.opsForValue().get(key);
        return count != null ? Long.parseLong(count.toString()) : 0;
    }

    public void reset(String key) {
        redisTemplate.delete(key);
    }
}
