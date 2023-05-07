package com.melody.supermarket.request;

import lombok.Data;

@Data
public class ResponseBody<T> {
    private Integer code;
    private String message;
    private T data;

    public ResponseBody() {
        this.code = Code.SERVER.getCode();
        this.message = Code.SERVER.name();
        this.data = null;
    }

    public ResponseBody(Code code) {
        this(code, null);
    }

    public ResponseBody(Code code, T data) {
        this.code = code.getCode();
        this.message = code.name();
        this.data = data;
    }
    public ResponseBody(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseBody(Integer code,String message) {
        this(code, message, null);
    }

    public ResponseBody(T data) {
        this(Code.SUCCESS, data);
    }
}
