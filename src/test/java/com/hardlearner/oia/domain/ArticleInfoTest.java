package com.hardlearner.oia.domain;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
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

    @Test
    public void update() {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(1L);
        articleDto.setArticleInfo(DummyData.dummyArticleInfo);
        articleDto.setContent(DummyData.dummyContent);

        Article article = new Article(1L,
                new ArticleInfo(Member.guest, "변경되기 전 제목", LocalDateTime.now()),
                new Content(Arrays.asList(new MainBlock(1L, Arrays.asList(new SubBlock(1L, null, null, null, false, null, null))))));

        Article updatedArticle = article.update(articleDto);
        assertEquals(updatedArticle.getTitle(), DummyData.dummyArticleInfo.getTitle());
    }
}
