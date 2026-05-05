package com.zqtravel.common.redis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Data
@ConfigurationProperties(prefix = "common.redis")
public class CommonRedisProperties {

    private Duration defaultExpire = Duration.ofHours(2);

    private Duration nullValueExpire = Duration.ofMinutes(30);

    private boolean cacheNullValue = true;

    private String keyPrefix = "zq";

    private LockProperties lock = new LockProperties();

    @Data
    public static class LockProperties {
        private Duration waitTime = Duration.ofSeconds(3);
        private Duration leaseTime = Duration.ofSeconds(30);
        private int retryTimes = 3;
        private long retryInterval = 100L;
    }
}
