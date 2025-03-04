package com.shop.exception;

import lombok.Getter;

@Getter
public class CustomFeignException extends RuntimeException {

    private final String code;
    private final String message;

    public CustomFeignException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
