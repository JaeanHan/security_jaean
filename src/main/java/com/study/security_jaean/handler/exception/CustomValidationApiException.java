package com.study.security_jaean.handler.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CustomValidationApiException extends RuntimeException{
    private Map<String, String> errMap;

    public CustomValidationApiException() {
        this("error", new HashMap<>());
    }

    public CustomValidationApiException(String message) {
        this(message, new HashMap<>());
    }

    public CustomValidationApiException(String message, Map<String, String> errMap) {
        super(message);
        this.errMap = errMap;
    }
}
