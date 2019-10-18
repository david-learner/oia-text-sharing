package com.hardlearner.oia.exception;

import com.hardlearner.oia.controller.HomeController;
import com.hardlearner.oia.exception.AuthenticationException;
import com.hardlearner.oia.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.net.URISyntaxException;

@ControllerAdvice
public class GlobalExceptionControllerAdvice {
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity unauthorized(UnauthorizedException e) throws URISyntaxException {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).location(new URI(HomeController.LOGIN_URL)).body(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity authentication(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity articleNotFoundException(ArticleNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
