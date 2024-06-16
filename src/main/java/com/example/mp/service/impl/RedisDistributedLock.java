package com.example.mp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisDistributedLock {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String acquireLock(String lockKey, long timeout, TimeUnit unit) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String lockValue = generateLockValue(); // 使用线程ID和时间戳生成唯一标识

        // 如果键不存在，设置键的值并返回true；如果键已经存在，则返回false
        Boolean success = ops.setIfAbsent(lockKey, lockValue, timeout, unit);

        if (success != null && success) {
            return lockValue; // 返回锁的唯一标识
        }
        return null;
    }

    public void releaseLock(String lockKey, String lockValue) {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        String currentValue = ops.get(lockKey);

        if (lockValue.equals(currentValue)) {
            stringRedisTemplate.delete(lockKey);
        }
    }

    private String generateLockValue() {
        return Thread.currentThread().getId() + "-" + System.currentTimeMillis();
    }

}
