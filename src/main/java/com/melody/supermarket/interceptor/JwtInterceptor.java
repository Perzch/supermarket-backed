package com.melody.supermarket.interceptor;

import com.melody.supermarket.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorization = request.getHeader("authorization");
        if(!StringUtils.hasText(authorization)) {
            throw new RuntimeException("未登录");
        }
        String token = authorization.replaceAll("Bearer ", "");
        if(!StringUtils.hasText(token)) {
            throw new RuntimeException("未登录");
        }
        try{
            return JwtUtil.verifyToken(token);
        } catch (Exception e) {
            throw new RuntimeException("token无效");
        }
    }
}
