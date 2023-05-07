package com.melody.supermarket.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.StandardCharsets;
import java.text.CharacterIterator;
import java.util.stream.Collector;

public class ResponseInterceptor implements HandlerInterceptor {
    private static final String METHODS = "Access-Control-Allow-Methods";
    private static final String MAX_AGE = "Access-Control-Max-Age";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.addHeader(METHODS, "GET, POST, PUT, DELETE");
        response.addHeader(MAX_AGE, "60");
        return true;
    }
}
