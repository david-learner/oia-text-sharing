package com.hardlearner.oia.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        this("사용자가 일치하지 않습니다");
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
