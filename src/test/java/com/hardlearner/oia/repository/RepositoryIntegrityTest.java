package com.hardlearner.oia.repository;

import com.hardlearner.oia.domain.Article;
import com.hardlearner.oia.domain.Content;
import com.hardlearner.oia.domain.MainBlock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RepositoryIntegrityTest {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    MainBlockRepository mainBlockRepository;
}
