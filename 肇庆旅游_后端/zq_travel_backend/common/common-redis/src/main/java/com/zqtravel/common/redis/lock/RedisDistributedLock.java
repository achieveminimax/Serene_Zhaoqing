package com.zqtravel.common.redis.lock;

import com.zqtravel.common.redis.config.CommonRedisProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
public class RedisDistributedLock {

    private final RedissonClient redissonClient;
    private final CommonRedisProperties properties;

    public boolean tryLock(String lockKey, Runnable runnable) {
        return tryLock(lockKey,
                properties.getLock().getWaitTime().getSeconds(),
                properties.getLock().getLeaseTime().getSeconds(),
                runnable);
    }

    public boolean tryLock(String lockKey, long waitTime, long leaseTime, Runnable runnable) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            boolean acquired = lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            if (acquired) {
                try {
                    runnable.run();
                    return true;
                } finally {
                    if (lock.isHeldByCurrentThread()) {
                        lock.unlock();
                    }
                }
            }
            log.warn("Failed to acquire lock: key={}", lockKey);
            return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Lock interrupted: key={}", lockKey, e);
            return false;
        }
    }

    public <T> T tryLockWithResult(String lockKey, Supplier<T> supplier) {
        return tryLockWithResult(lockKey,
                properties.getLock().getWaitTime().getSeconds(),
                properties.getLock().getLeaseTime().getSeconds(),
                supplier);
    }

    public <T> T tryLockWithResult(String lockKey, long waitTime, long leaseTime, Supplier<T> supplier) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            boolean acquired = lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            if (acquired) {
                try {
                    return supplier.get();
                } finally {
                    if (lock.isHeldByCurrentThread()) {
                        lock.unlock();
                    }
                }
            }
            log.warn("Failed to acquire lock: key={}", lockKey);
            return null;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Lock interrupted: key={}", lockKey, e);
            return null;
        }
    }

    public void lock(String lockKey, Runnable runnable) {
        lock(lockKey, properties.getLock().getLeaseTime().getSeconds(), runnable);
    }

    public void lock(String lockKey, long leaseTime, Runnable runnable) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.lock(leaseTime, TimeUnit.SECONDS);
            try {
                runnable.run();
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        } catch (Exception e) {
            log.error("Lock error: key={}", lockKey, e);
            throw new RuntimeException("Lock error: " + lockKey, e);
        }
    }

    public boolean isLocked(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        return lock.isLocked();
    }
}
