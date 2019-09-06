package com.hardlearner.oia.domain;

import org.junit.Test;

import java.time.LocalDateTime;

public class ArticleInfoTest {
    @Test
    public void create() {
        Member writer = new Member();
        String title = "본깨적에 대한 이해";
        LocalDateTime localDateTime = LocalDateTime.now();
        ArticleInfo articleInfo = new ArticleInfo(writer, title, localDateTime);
    }
}
