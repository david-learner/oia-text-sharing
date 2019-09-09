package com.hardlearner.oia.domain;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static org.junit.Assert.*;


public class ArticleTest {
    private static final Logger log = LoggerFactory.getLogger(ArticleTest.class);

    Member writer = new Member("oia@gmail.com", "OiaTest0123", "오아이");

    @Test
    public void print() {
        String title = "본깨적에 대한 이해";
        LocalDateTime dateTime = LocalDateTime.now();
        ArticleInfo articleInfo = new ArticleInfo(writer, title, dateTime);
        Content content = new Content(DummyData.mainBlocks1);
        Article article = new Article(articleInfo, content);
        log.debug(article.print());

        String dummyDataSubBlockContent = "본깨적이란 본 것과 깨달은 것 그리고 적용할 것입니다";
        assertTrue(article.print().contains(title));
        assertTrue(article.print().contains(dummyDataSubBlockContent));
    }
}
