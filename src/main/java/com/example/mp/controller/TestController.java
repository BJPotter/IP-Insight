package com.example.mp.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
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
    @RequestMapping("/test")
    public String test(){
        return "test";
    }
}
