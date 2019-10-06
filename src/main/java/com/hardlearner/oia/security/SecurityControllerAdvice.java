package com.hardlearner.oia.security;

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
}
