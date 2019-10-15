package com.hardlearner.oia.repository;

import com.hardlearner.oia.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ArticleRepositoryTest {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void save() {
        Member member = new Member("articlerepo@gmail.com", "password", "김더미");
        List<SubBlock> subBlocks = Arrays.asList(
                SubBlock.builder()
                        .pages(new Pages(1, 1))
                        .share(false)
                        .category(ContentCategory.OBSERVATION)
                        .content("코드로 익히는 객체지향 설계").build());
        List<MainBlock> mainBlocks = Arrays.asList(
                MainBlock.builder()
                        .pointers(new Pointers(null, null))
                        .subBlocks(subBlocks).build());
        Article article = new Article(
                new ArticleInfo(member, "조영호의 오브젝트", LocalDateTime.now()),
                new Content(mainBlocks));
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
        Pageable first = PageRequest.of(0, PageInfo.BLOCK_SIZE);
        List<Article> articles = articleRepository.findAllByArticleInfo_Writer(member, first);
        assertTrue(articles.size() == PageInfo.BLOCK_SIZE);
    }
}
