package com.zqtravel.user.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JWT令牌提供者
 * 负责生成、验证和解析JWT令牌
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Slf4j
@Component
public class JwtTokenProvider {

    /**
     * JWT密钥（生产环境应从配置中心获取）
     */
    @Value("${jwt.secret:zqtravel-secret-key-change-in-production}")
    private String secret;

    /**
     * Access Token有效期（毫秒），默认2小时
     */
    @Value("${jwt.access-token-validity:7200000}")
    private long accessTokenValidity;

    /**
     * Refresh Token有效期（毫秒），默认7天
     */
    @Value("${jwt.refresh-token-validity:604800000}")
    private long refreshTokenValidity;

    /**
     * 生成Access Token
     * 
     * @param username 用户名（手机号）
     * @param userId 用户ID
     * @return Access Token字符串
     */
    public String generateAccessToken(String username, Long userId) {
        return generateToken(username, userId, accessTokenValidity);
    }

    /**
     * 生成Refresh Token
     * 
     * @param username 用户名（手机号）
     * @param userId 用户ID
     * @return Refresh Token字符串
     */
    public String generateRefreshToken(String username, Long userId) {
        return generateToken(username, userId, refreshTokenValidity);
    }

    /**
     * 生成JWT令牌
     * 
     * @param username 用户名
     * @param userId 用户ID
     * @param validity 有效期（毫秒）
     * @return JWT令牌字符串
     */
    private String generateToken(String username, Long userId, long validity) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + validity);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 从令牌中提取用户名
     * 
     * @param token JWT令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * 从令牌中提取用户ID
     * 
     * @param token JWT令牌
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("userId", Long.class);
    }

    /**
     * 从令牌中提取过期时间
     * 
     * @param token JWT令牌
     * @return 过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 验证令牌是否有效
     * 
     * @param token JWT令牌
     * @param userDetails 用户详情
     * @return 是否有效
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * 验证令牌是否有效（不依赖用户详情）
     * 
     * @param token JWT令牌
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT令牌验证失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 检查令牌是否过期
     * 
     * @param token JWT令牌
     * @return 是否过期
     */
    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 从令牌中提取指定声明
     * 
     * @param token JWT令牌
     * @param claimsResolver 声明解析函数
     * @param <T> 声明类型
     * @return 声明值
     */
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 从令牌中提取所有声明
     * 
     * @param token JWT令牌
     * @return 所有声明
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取签名密钥
     * 
     * @return 签名密钥
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 获取Access Token有效期（毫秒）
     */
    public long getAccessTokenValidity() {
        return accessTokenValidity;
    }

    /**
     * 获取Refresh Token有效期（毫秒）
     */
    public long getRefreshTokenValidity() {
        return refreshTokenValidity;
    }
}