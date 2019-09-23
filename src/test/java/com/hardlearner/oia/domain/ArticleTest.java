package com.hardlearner.oia.domain;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class ArticleTest {
    private static final Logger log = LoggerFactory.getLogger(ArticleTest.class);

    Member writer = new Member("oia@gmail.com", "OiaTest0123", "오아이");

    @Test
    public void print() {
        String title = "본깨적에 대한 이해";
        String dummyDataSubBlockContent = "본깨적이란 본 것과 깨달은 것 그리고 적용할 것입니다";
        ArticleInfo articleInfo = new ArticleInfo(writer, title, LocalDateTime.now());
        Content content = new Content(DummyData.dummyMainBlocks1);
        Article article = new Article(articleInfo, content);
        log.debug(article.toString());

        assertTrue(article.toString().contains(title));
        assertTrue(article.toString().contains(dummyDataSubBlockContent));
    }

    @Test
    public void shareAllowed() throws AuthenticationException {
        // given
        List<SubBlock> subBlocks = Arrays.asList(
                new SubBlock(new Pages(10, 10), true, ContentCategory.OBSERVATION, "본 것은 책에 쓰여진 내용을 저자의 관점으로 읽는 것입니다"),
                new SubBlock(new Pages(10, 10), false, ContentCategory.INTERPRETATION, "처음부터 저자의 관점으로 보기 힘들다. 책을 많이 읽다보면 자연스레 해당 컨텐스트에서 작가가 강조하고자 하는 것이 무엇인지 보인다."),
                new SubBlock(new Pages(10, 10), true, ContentCategory.APPLICATION, "책 읽을 때 펜을 들고 해당 문장, 문단 또는 챕터에서 자신이 생각하기에 저자가 강조한 내용을 밑줄치기")
        );
        List<MainBlock> mainBlocks = Arrays.asList(new MainBlock(1L, subBlocks));
        ArticleInfo articleInfo = new ArticleInfo(writer, "본깨적에 대한 이해", LocalDateTime.now());
        Content content = new Content(mainBlocks);
        Article article = new Article(articleInfo, content);

        // when
        Article shareAllowedArticle = article.getShareAllowed(writer);

        log.debug(shareAllowedArticle.toString());
        // then
        assertTrue(shareAllowedArticle.toString().contains("본 것은 책에 쓰여진 내용을"));
        assertFalse(shareAllowedArticle.toString().contains("처음부터 저자의 관점으로"));
        assertTrue(shareAllowedArticle.toString().contains("책 읽을 때 펜을 들고"));
    }

    @Test
    public void get_default_article_for_writing() {
        Article defaultArticle = Article.getDefaultArticle(DummyData.dummyMember, DummyData.dummyMainBlock1);
        assertEquals(defaultArticle.getTitle(), "제목없는 문서");
//        assertEquals(defaultArticle.getContent().toString(), );
        // print와 toString을 구분해서 만들기
    }

    @Test
    public void createFullArticle() {
        Article defaultArticle = new Article(DummyData.dummyArticleInfo, null);
    }
}
