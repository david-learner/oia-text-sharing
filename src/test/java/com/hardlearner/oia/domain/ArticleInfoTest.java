package com.hardlearner.oia.domain;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

public class ArticleInfoTest {
    @Test
    public void create() {
        Member writer = new Member("oia@gmail.com", "OiaTest0123", "오아이");
        String title = "본깨적에 대한 이해";
        ArticleInfo articleInfo = new ArticleInfo(writer, title, LocalDateTime.now());
    }

    @Test
    public void print() {
        Member writer = new Member("oia@gmail.com", "OiaTest0123", "오아이");
        String title = "본깨적에 대한 이해";
        ArticleInfo articleInfo = new ArticleInfo(writer, title, LocalDateTime.now());

        assertTrue(articleInfo.toString().contains(title));
    }
}
