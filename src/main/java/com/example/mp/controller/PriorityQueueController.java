package com.example.mp.controller;

import com.example.mp.common.ApiResponse;
import com.example.mp.pojo.PriorityQueue;
import com.example.mp.service.impl.RedisPriorityQueueService;
import com.example.mp.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Zhi Liu
 * Date: 2024/6/17 12:55
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@RestController
@RequestMapping("/test")
@Slf4j
@Api(value = "PriorityQueue Controller", tags = {"优先级队列"})
public class PriorityQueueController {
    @Autowired
    private RedisPriorityQueueService priorityQueueService;

    @PostMapping("/enqueue")
    @ApiOperation(value = "进入优先级队列",notes = "入队")
    public ApiResponse<String> enqueue(@RequestBody PriorityQueue priorityQueue) {
        priorityQueueService.enqueue(priorityQueue.getQueueName(), priorityQueue.getElement(), priorityQueue.getPriority());
        return ResponseUtil.success("Element enqueued");
    }

    @GetMapping("/dequeue")
    @ApiOperation(value = "从优先级队列出对",notes = "出队")
    public ApiResponse<String> dequeue(@RequestParam String queueName) {
        String element = priorityQueueService.dequeue(queueName);
        return element != null ? ResponseUtil.success("Dequeued element: "+ element)  : ResponseUtil.success("Queue is empty");
    }
}
