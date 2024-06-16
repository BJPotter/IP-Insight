package com.example.mp.controller;

import com.example.mp.common.ApiResponse;
import com.example.mp.service.impl.RedisDistributedLock;
import com.example.mp.util.ResponseUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
@Slf4j
@Api(value = "DistributedLock Controller", tags = {"DistributedLock"})
public class DistributedLockController {
    @Autowired
    private RedisDistributedLock redisDistributedLock;

    @GetMapping("/testLock")
    public ApiResponse<String> testLock(){
        String lockKey = "my_lock";
        String lockValue = redisDistributedLock.acquireLock(lockKey, 10, TimeUnit.SECONDS);

        if (lockValue != null) {
            try {
                // 执行需要加锁的代码

                log.info("Lock acquired, executing critical section.");
                // 模拟处理时间
                Thread.sleep(5000);

                return ResponseUtil.success("Lock acquired and released successfully.");
            } catch (InterruptedException e) {
                e.printStackTrace();

                return ResponseUtil.error(500, "Error occurred.", e.getMessage());
            } finally {
                redisDistributedLock.releaseLock(lockKey, lockValue);

                log.info("Lock released.");
            }
        } else {

            return ResponseUtil.error(500, "Error occurred.", "Failed to acquire lock.");
        }
    }

}
