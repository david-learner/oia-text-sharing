package com.hardlearner.oia.domain;

import org.junit.Test;

import java.time.LocalDateTime;

public class ArticleTest {
    @Test
    public void create() {
        Member writer = new Member();
        String title = "본깨적에 대한 이해";
        LocalDateTime dateTime = LocalDateTime.now();
        ArticleInfo articleInfo = new ArticleInfo(writer, title, dateTime);
        Content content = new Content();
        Article article = new Article(articleInfo, content);
    }
}
