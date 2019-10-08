package com.hardlearner.oia.repository;

import com.hardlearner.oia.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ArticleRepositoryTest {
    private static final Logger log =  LoggerFactory.getLogger(ArticleRepositoryTest.class);

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void save() {
        Article article = new Article(new ArticleInfo(DummyData.dummyMember, "토비의 스프링 리뷰", LocalDateTime.now()), new Content(DummyData.dummyMainBlocks1));
        assertNull(article.getId());
        Article saveArticle = articleRepository.save(article);
        assertNotNull(saveArticle.getId());
    }

    @Test
    public void getArticles() {
        Member member = memberRepository.findById(1L).orElseThrow(NullPointerException::new);
        List<Article> articles = articleRepository.findAllByArticleInfo_Writer(member);
        assertTrue(articles.size() > 0);
    }

    @Test
    public void pageable() {
        Member member = memberRepository.findById(1L).orElseThrow(NullPointerException::new);
        Pageable first = PageRequest.of(0, 3);
        List<Article> articles = articleRepository.findAllByArticleInfo_Writer(member, first);
        assertTrue(articles.size() == 3);
    }
}
