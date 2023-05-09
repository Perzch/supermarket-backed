package com.melody.supermarket.exception;

public class ParameterException extends RuntimeException {
    public Integer getCode() {
        return code;
    }

    private final Integer code = 500;
    public ParameterException() {
        super();
    }

    public ParameterException(String s) {
        super(s);
    }
}
