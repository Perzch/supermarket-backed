package com.melody.supermarket.interceptor;

import com.melody.supermarket.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtInterceptor implements HandlerInterceptor {
    private static final String METHODS = "Access-Control-Allow-Methods";
    private static final String MAX_AGE = "Access-Control-Max-Age";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("authorization");
        String token = authorization.replaceAll("Bearer ", "");
        if(!StringUtils.hasText(token)) {
            return false;
        }
        return JwtUtil.verifyToken(token);
    }
}
