package com.example.mp.controller;

import com.example.mp.pojo.User;
import com.example.mp.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Zhi Liu
 * Date: 2024/5/28 13:08
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@RestController
@RequestMapping("/auth")
@Api(value = "register Controller", tags = {"register"})
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return "User registered successfully";
    }
}