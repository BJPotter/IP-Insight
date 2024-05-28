package com.example.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.mp.pojo.JwtUserDetails;
import com.example.mp.pojo.User;
import com.example.mp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



/**
 * Author: Zhi Liu
 * Date: 2024/5/27 16:53
 * Contact: liuzhi0531@gmail.com
 * Desc: 创建一个用户认证服务，用于加载用户信息
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;

    // 根据用户名加载用户
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        LambdaQueryWrapper<User> queryWrapper = lambdaQueryWrapper
                .eq(User::getUsername, username);

        User user = userService.getOne(queryWrapper);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new JwtUserDetails(user); // user是你自定义的UserDetails实现类
    }
}
