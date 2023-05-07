package com.melody.supermarket.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private static String key;
    @Value("${jwt.key}")
    public void setKey(String key) {
        JwtUtil.key = key;
    }

    public static String createToken(String username) {
        DateTime date = DateTime.now();
        DateTime newDate = date.offsetNew(DateField.HOUR, 10);
        Map<String, Object> payload = new HashMap<>();
//        签发日期
        payload.put(JWTPayload.ISSUED_AT, date);
//        过期日期
        payload.put(JWTPayload.EXPIRES_AT, newDate);
//        生效时间
        payload.put(JWTPayload.NOT_BEFORE, date);
//        用户名
        payload.put("username", username);
        return JWTUtil.createToken(payload, key.getBytes());
    }

    public static boolean verifyToken(String token) {
        return JWTUtil.verify(token, key.getBytes());
    }
}
