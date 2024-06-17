package com.example.mp.pojo;

import lombok.Data;

/**
 * Author: Zhi Liu
 * Date: 2024/6/17 13:00
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@Data
public class PriorityQueue {
    String queueName;
    String element;
    // 越小约优先级越高
    Double priority;
}
