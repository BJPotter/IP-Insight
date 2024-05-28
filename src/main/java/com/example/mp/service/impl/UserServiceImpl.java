package com.example.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mp.mapper.UserMapper;
import com.example.mp.pojo.User;
import com.example.mp.service.UserService;
import com.example.mp.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Author: Zhi Liu
 * Date: 2024/5/27 16:06
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void registerUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        // 将用户名和加密后的密码存储到数据库中
        User passwdUser = new User();
        passwdUser.setUsername(user.getUsername());
        passwdUser.setPassword(encodedPassword);
        userMapper.insert(passwdUser);
    }
}
