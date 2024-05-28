package com.example.mp.util;

import com.example.mp.annotation.CacheResult;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.*;


/**
 * Author: Zhi Liu
 * Date: 2024/5/27 16:41
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
@Component
@Slf4j
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;


    // 生成JWT令牌
    @CacheResult(cacheKey = "#this.getCacheKey(#username)",expireTime = 1000 * 60 * 60 * 10)
    public String generateToken(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        return token;
    }

    public String getCacheKey(String username){
        return "token:"+username;
    }

    // 提取声明（claims）
    public Claims extractClaims(String token) {
        try {
            log.info("Extracting claims from Token: {}", token); // 输出接收到的令牌
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            return null;
        } catch (Exception e) {
            log.error("Error extracting claims: {}", e.getMessage());
            return null;
        }
    }

    // 验证JWT令牌
    public boolean validateToken(String token, String username) {
        Claims claims = extractClaims(token);
        if (claims == null) {
            return false;
        }
        String tokenUsername = claims.getSubject();
        boolean isTokenExpired = claims.getExpiration().before(new Date());
        boolean isValid = username.equals(tokenUsername) && !isTokenExpired;
        if (!isValid) {
            log.warn("Token validation failed. Username: {}, Token Username: {}, Is Token Expired: {}", username, tokenUsername, isTokenExpired);
        }
        return isValid;
    }

}
