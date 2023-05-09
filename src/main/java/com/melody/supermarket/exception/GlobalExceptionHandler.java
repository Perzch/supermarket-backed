package com.melody.supermarket.exception;

import com.melody.supermarket.request.ResponseBody;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ResponseBody<?>> exceptionHandler(Exception e) {
        return ResponseEntity.internalServerError().body(new ResponseBody<>(500, e.getMessage(), null));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ParameterException.class})
    public ResponseEntity<ResponseBody<?>> exceptionHandler(ParameterException e) {
        return ResponseEntity.badRequest().body(new ResponseBody<>(e.getCode(), e.getMessage(), null));
    }

    /**
     * 忽略参数异常处理器
     *
     * @param e 忽略参数异常
     * @return ResponseEntity
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ResponseBody<?>> parameterMissingExceptionHandler(MissingServletRequestParameterException e) {
        return ResponseEntity.badRequest().body(new ResponseBody<>(400, e.getMessage(), null));
    }

    /**
     * 缺少请求体异常处理器
     *
     * @param e 缺少请求体异常
     * @return ResponseEntity
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseBody<?>> parameterBodyMissingExceptionHandler(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body(new ResponseBody<>(400, e.getMessage(), null));
    }

    /**
     * 参数效验异常处理器
     *
     * @param e 参数验证异常
     * @return ResponseEntity
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseBody<?>> parameterExceptionHandler(MethodArgumentNotValidException e) {
        // 获取异常信息
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                List<String> errorsMessage = new ArrayList<>();
                errors.forEach(err -> errorsMessage.add(err.getDefaultMessage()));
                return ResponseEntity.badRequest().body(new ResponseBody<>(400, StringUtils.join(errorsMessage,','), null));
            }
        }
        return ResponseEntity.badRequest().body(new ResponseBody<>(400, e.getMessage(), null));
    }
}
