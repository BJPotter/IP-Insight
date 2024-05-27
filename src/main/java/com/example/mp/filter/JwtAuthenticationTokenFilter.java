package com.example.mp.filter;

import com.example.mp.util.JwtUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: Zhi Liu
 * Date: 2024/5/27 17:32
 * Contact: liuzhi0531@gmail.com
 * Desc:
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    public JwtAuthenticationTokenFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
