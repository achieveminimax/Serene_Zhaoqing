package com.zqtravel.user.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 令牌黑名单服务
 * 使用Redis存储已登出的令牌
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private final RedisTemplate<String, String> redisTemplate;
    
    private static final String BLACKLIST_KEY_PREFIX = "zq:user:token:blacklist:";
    
    /**
     * 将令牌加入黑名单
     * 
     * @param token JWT令牌
     * @param expirationTime 令牌过期时间（毫秒）
     */
    public void addToBlacklist(String token, long expirationTime) {
        String key = BLACKLIST_KEY_PREFIX + token;
        
        // 计算令牌剩余有效期
        long ttl = calculateTtl(expirationTime);
        if (ttl > 0) {
            redisTemplate.opsForValue().set(key, "blacklisted", ttl, TimeUnit.MILLISECONDS);
            log.debug("令牌已加入黑名单，剩余有效期: {} 毫秒", ttl);
        }
    }
    
    /**
     * 检查令牌是否在黑名单中
     * 
     * @param token JWT令牌
     * @return 是否在黑名单中
     */
    public boolean isBlacklisted(String token) {
        String key = BLACKLIST_KEY_PREFIX + token;
        Boolean exists = redisTemplate.hasKey(key);
        return exists != null && exists;
    }
    
    /**
     * 从黑名单中移除令牌（用于测试或特殊情况）
     * 
     * @param token JWT令牌
     */
    public void removeFromBlacklist(String token) {
        String key = BLACKLIST_KEY_PREFIX + token;
        redisTemplate.delete(key);
        log.debug("令牌已从黑名单中移除");
    }
    
    /**
     * 计算令牌剩余有效期
     * 
     * @param expirationTime 令牌过期时间戳（毫秒）
     * @return 剩余有效期（毫秒）
     */
    private long calculateTtl(long expirationTime) {
        long currentTime = System.currentTimeMillis();
        long ttl = expirationTime - currentTime;
        
        // 如果令牌已过期，不需要加入黑名单
        if (ttl <= 0) {
            log.debug("令牌已过期，无需加入黑名单");
            return 0;
        }
        
        return ttl;
    }
}