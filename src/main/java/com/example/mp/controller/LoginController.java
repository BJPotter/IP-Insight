package com.example.mp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.mp.common.ApiResponse;
import com.example.mp.pojo.User;
import com.example.mp.service.UserService;
import com.example.mp.service.impl.JwtUserDetailsService;
import com.example.mp.util.JwtUtils;
import com.example.mp.util.ResponseUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Author: Zhi Liu
 * Date: 2024/5/23 17:29
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */


@RestController
@RequestMapping("/auth")
@Slf4j
@Api(value = "login Controller", tags = {"login"})
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;



    // 处理用户登录请求
    @PostMapping("/login")
    public ApiResponse<String> createToken(@RequestBody User authRequest) throws Exception {
        // 认证阶段
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new Exception("Invalid username or password");
        }
        // 生成JWT令牌
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtUtils.generateToken(userDetails.getUsername());
        return ResponseUtil.success(token);
    }
}