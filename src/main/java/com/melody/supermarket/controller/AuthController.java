package com.melody.supermarket.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.melody.supermarket.exception.ParameterException;
import com.melody.supermarket.pojo.User;
import com.melody.supermarket.request.Code;
import com.melody.supermarket.request.ResponseBody;
import com.melody.supermarket.services.UserServices;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserServices userServices;

//    验证码对象
    private LineCaptcha lineCaptcha;

    @PostMapping
    public ResponseEntity<ResponseBody<String>> login(@RequestBody @Valid User u) {
        try{
//            验证码错误，抛出验证码错误异常
            if(!lineCaptcha.verify(u.getCaptcha())) {
                throw new ParameterException(Code.CAPTCHA_ERROR.getMsg());
            }
        } catch (Exception e) {
            throw new ParameterException(Code.CAPTCHA_ERROR.getMsg());
        }
        String token = userServices.verifyPassword(u);
        return Objects.nonNull(token) ?
                    ResponseEntity.ok(new ResponseBody<>(token)) :
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
