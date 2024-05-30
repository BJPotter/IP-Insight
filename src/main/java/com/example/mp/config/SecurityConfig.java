package com.example.mp.config;

import com.example.mp.filter.JwtRequestFilter;
import com.example.mp.service.impl.JwtUserDetailsService;
import com.example.mp.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Author: Zhi Liu
 * Date: 2024/5/27 17:32
 * Contact: liuzhi0531@gmail.com
 * Desc: 1.禁用CSRF保护（对于无状态的REST API来说，CSRF保护通常是多余的）。
 *       2.配置HTTP安全性，定义哪些端点需要认证，哪些端点不需要认证。
 *       3.配置会话管理策略为无状态（STATELESS）。
 *       4.注册JWT过滤器，确保在每次请求时验证JWT令牌。
 *       5.配置用户认证服务和密码编码器。
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/login", "/auth/register","/swagger-ui.html**", "/swagger-resources/**", "/v2/api-docs", "/webjars/**").permitAll() // 允许匿名访问登录和注册路径
                .anyRequest().authenticated()
                .and()
                //注册过滤器
                .addFilterBefore(new JwtRequestFilter(jwtUtils, userDetailsService), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}