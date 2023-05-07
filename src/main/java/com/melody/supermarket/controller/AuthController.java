package com.melody.supermarket.controller;

import cn.hutool.Hutool;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.melody.supermarket.pojo.User;
import com.melody.supermarket.repository.UserRepository;
import com.melody.supermarket.request.Code;
import com.melody.supermarket.request.ResponseBody;
import com.melody.supermarket.util.JwtUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

//    验证码对象
    private LineCaptcha lineCaptcha;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<ResponseBody<String>> login(@RequestBody User u) {
//        如果用户名或密码或验证码为空
        if(!StringUtils.hasText(u.getUsername())||!StringUtils.hasText(u.getPassword())||!StringUtils.hasText(u.getCaptcha()))
            return ResponseEntity.ok(new ResponseBody<>(Code.PARAMETER));
//        验证验证码
        if(!lineCaptcha.verify(u.getCaptcha()))
            return ResponseEntity.ok(new ResponseBody<>(Code.CAPTCHA_ERROR));
        User user = userRepository.findUserByUsername(u.getUsername());
        if(Objects.isNull(user)) {
            return ResponseEntity.ok(new ResponseBody<>(Code.USER_NOT_FOUND));
        } else
            return user.getPassword().equals(u.getPassword()) ?
                    ResponseEntity.ok(new ResponseBody<>(JwtUtil.createToken(user.getUsername()))) :
                    ResponseEntity.ok(new ResponseBody<>(Code.PASSWORD_ERROR));
    }

    @SneakyThrows
    @GetMapping("/captcha")
//    生成验证码图像
    public void captcha(HttpServletResponse response) {
        lineCaptcha = CaptchaUtil.createLineCaptcha(200,100);
        response.setContentType("image/jpg");
        response.setHeader("Pragma","no-cache");
        try(ServletOutputStream outputStream = response.getOutputStream()) {
            lineCaptcha.write(outputStream);
        }
    }
}
