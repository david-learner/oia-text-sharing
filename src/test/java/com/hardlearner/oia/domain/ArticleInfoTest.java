package com.hardlearner.oia.domain;

import com.hardlearner.oia.dto.ArticleDto;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ArticleInfoTest {
    @Test
    public void create() {
        Member writer = new Member("david@gmail.com", "davidpassword", "데이빗");
        String title = "조영호의 오브젝트 (코드로 이해하는 객체지향 설계)";
        ArticleInfo articleInfo = new ArticleInfo(writer, title, LocalDateTime.now());

        assertNotNull(articleInfo);
    }

    @Test
    public void update() {
        ArticleDto articleDto = new ArticleDto(1L, DummyData.DUMMY_ARTICLE_INFO, DummyData.DUMMY_CONTENT);
        Article article = new Article(1L,
                new ArticleInfo(Member.GUEST_MEMBER, "변경되기 전 제목", LocalDateTime.now()),
                new Content(DummyData.DUMMY_MAIN_BLOCKS));

        Article updatedArticle = article.update(articleDto);

        assertEquals(updatedArticle.getTitle(), DummyData.DUMMY_ARTICLE_INFO.getTitle());
    }
}
