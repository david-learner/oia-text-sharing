package com.hardlearner.oia.repository;

import com.hardlearner.oia.domain.Article;
import com.hardlearner.oia.domain.DummyData;
import com.hardlearner.oia.domain.Member;
import lombok.Builder;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@Sql({"/import.sql"})
public class RepositoryIntegrityTest {
    private static final Logger log = LoggerFactory.getLogger(RepositoryIntegrityTest.class);

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    MainBlockRepository mainBlockRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    SubBlockRepository subBlockRepository;

    @Test
    public void getArticleWithBlocks() {
        Article savedArticle1 = articleRepository.save(DummyData.dummyArticle);
        Article savedArticle2 = articleRepository.getOne(savedArticle1.getId());
        log.debug("savedArticle2 : " + savedArticle2.toString());
    }
}