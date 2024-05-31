package com.example.mp.config;

import com.example.mp.interceptor.LoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Author: Zhi Liu
 * Date: 2024/5/31 11:28
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoggingInterceptor loggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(loggingInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/v2/api-docs",
                "/v3/api-docs",
                "/webjars/**"
        );
    }
}
