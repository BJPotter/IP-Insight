package com.example.mp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Zhi Liu
 * Date: 2024/5/22 14:03
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheResult {
    String cacheKey() default ""; // 缓存 Key
    long expireTime() default 30 * 60; // 缓存过期时间,默认 30 分钟
}
