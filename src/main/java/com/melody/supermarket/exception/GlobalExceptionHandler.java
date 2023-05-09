package com.melody.supermarket.exception;

import com.melody.supermarket.request.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseEntity<ResponseBody<?>> exceptionHandler(Exception e) {
        return ResponseEntity.ok(new ResponseBody<>(500, e.getMessage(), null));
    }

    @ExceptionHandler(value = {ParameterException.class})
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseEntity<ResponseBody<?>> exceptionHandler(ParameterException e) {
        return ResponseEntity.ok(new ResponseBody<>(e.getCode(), e.getMessage(), null));
    }
}
