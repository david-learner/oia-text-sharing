package com.hardlearner.oia.security;

import com.hardlearner.oia.exception.AuthenticationException;
import com.hardlearner.oia.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SecurityControllerAdvice {
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity unauthorized(UnauthorizedException ue) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ue.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity authentication(AuthenticationException ae) {
        // todo error가 나면 error 페이지로 리턴하고 status에 맞는 문구를 error 페이지에서 출력하고 로그인 유도
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ae.getMessage());
    }
}
