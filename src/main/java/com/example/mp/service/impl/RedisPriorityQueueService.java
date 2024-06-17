package com.example.mp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Author: Zhi Liu
 * Date: 2024/6/17 12:54
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@Service
public class RedisPriorityQueueService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final RedisScript<Long> enqueueScript;
    private final RedisScript<String> dequeueScript;

    public RedisPriorityQueueService() {
        this.enqueueScript = new DefaultRedisScript<>();
        ((DefaultRedisScript<Long>) this.enqueueScript).setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/enqueue.lua")));
        ((DefaultRedisScript<Long>) this.enqueueScript).setResultType(Long.class);

        this.dequeueScript = new DefaultRedisScript<>();
        ((DefaultRedisScript<String>) this.dequeueScript).setScriptSource(new ResourceScriptSource(new ClassPathResource("scripts/dequeue.lua")));
        ((DefaultRedisScript<String>) this.dequeueScript).setResultType(String.class);
    }

    public void enqueue(String queueName, String element, double priority) {
        redisTemplate.execute(enqueueScript, Collections.singletonList(queueName), element, String.valueOf(priority));
    }

    public String dequeue(String queueName) {
        return redisTemplate.execute(dequeueScript, Collections.singletonList(queueName));
    }
}
