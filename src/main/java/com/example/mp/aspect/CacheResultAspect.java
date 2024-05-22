package com.example.mp.aspect;

import com.example.mp.annotation.CacheResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Author: Zhi Liu
 * Date: 2024/5/22 14:04
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@Aspect
@Component
public class CacheResultAspect {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final ExpressionParser parser = new SpelExpressionParser();

    @Around("@annotation(cacheResult)")
    public Object cacheResult(ProceedingJoinPoint joinPoint, CacheResult cacheResult) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        String cacheKeyExpression = cacheResult.cacheKey();
        Expression expression = parser.parseExpression(cacheKeyExpression);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setRootObject(joinPoint.getTarget());
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        String cacheKey = expression.getValue(context, String.class);

        Object cacheValue = redisTemplate.opsForValue().get(cacheKey);

        if (cacheValue != null) {
            return cacheValue;
        }

        Object result = joinPoint.proceed();
        redisTemplate.opsForValue().set(cacheKey, result, cacheResult.expireTime(), TimeUnit.SECONDS);

        return result;
    }
}

