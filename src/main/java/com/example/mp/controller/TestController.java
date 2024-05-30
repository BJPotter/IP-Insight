package com.example.mp.controller;

import com.example.mp.common.ApiResponse;
import com.example.mp.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Zhi Liu
 * Date: 2024/5/27 16:31
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@RestController
@Api(value = "test Controller", tags = {"test"})
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/test")
    @ApiOperation(value = "test Method", tags = {"方法测试"})
    public ApiResponse<String> test(){
        logger.info("Handling /test request");
        return ResponseUtil.success("test");
    }
}
