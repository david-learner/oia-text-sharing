package com.hardlearner.oia.exception;

public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException() {
        super("요청하신 아티클이 존재하지 않습니다");
    }
}
