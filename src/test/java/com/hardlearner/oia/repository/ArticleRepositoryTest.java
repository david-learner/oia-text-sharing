package com.hardlearner.oia.repository;

import com.hardlearner.oia.domain.Article;
import com.hardlearner.oia.domain.ArticleInfo;
import com.hardlearner.oia.domain.Content;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ArticleRepositoryTest {
    @Autowired
    ArticleRepository articleRepository;

    @Test
    public void save() {
        Article article = new Article(new ArticleInfo(null, "토비의 스프링 리뷰", LocalDateTime.now()), new Content(null));
        assertNull(article.getId());
        Article saveArticle = articleRepository.save(article);
        assertNotNull(saveArticle.getId());
    }
}
