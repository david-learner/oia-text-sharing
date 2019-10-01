package com.hardlearner.oia.service;

import com.hardlearner.oia.domain.Article;
import com.hardlearner.oia.domain.MainBlock;
import com.hardlearner.oia.domain.Member;
import com.hardlearner.oia.domain.SubBlock;
import com.hardlearner.oia.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleService {
    private static final Logger log =  LoggerFactory.getLogger(ArticleService.class);

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    MainBlockService mainBlockService;
    @Autowired
    SubBlockService subBlockService;

    public Article create(Member loginMember) {
        List<SubBlock> subBlocks = subBlockService.saveAll(SubBlock.getDefaultSubBlocks());
        MainBlock mainBlock = mainBlockService.save(MainBlock.getDefaultMainBlock(subBlocks));
        Article savedArticle = articleRepository.save(Article.getDefaultArticle(loginMember, mainBlock));
        return savedArticle;
    }

    public void save(Article article) {
        articleRepository.save(article);
    }

    public Article getArticle(Long id) {
        // return articleRepository.getOne(id); // LazyLoading 때문에 json타입으로 변환할 때 에러 발생
        return articleRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
