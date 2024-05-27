package com.example.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.mp.pojo.User;
import com.example.mp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Author: Zhi Liu
 * Date: 2024/5/27 16:53
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        LambdaQueryWrapper<User> eq = lambdaQueryWrapper
                .eq(User::getUsername, username);
        User loginUser = userService.getOne(eq);
        if(loginUser == null){
            throw new UsernameNotFoundException(String.format("'%s'.这个用户不存在", username));
        }

        return new org.springframework.security.core.userdetails.User(
                loginUser.getUsername(),
                passwordEncoder.encode(loginUser.getPassword()),
                Collections.emptyList()
        );
    }
}
