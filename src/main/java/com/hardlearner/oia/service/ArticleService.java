package com.hardlearner.oia.service;

import com.hardlearner.oia.domain.*;
import com.hardlearner.oia.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class ArticleService {
    private static final Logger log = LoggerFactory.getLogger(ArticleService.class);

    private ArticleRepository articleRepository;
    private MainBlockService mainBlockService;
    private SubBlockService subBlockService;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, MainBlockService mainBlockService, SubBlockService subBlockService) {
        this.articleRepository = articleRepository;
        this.mainBlockService = mainBlockService;
        this.subBlockService = subBlockService;
    }

    public Article create(Member loginMember) {
        List<SubBlock> subBlocks = subBlockService.saveAll(SubBlock.getDefaultSubBlocks());
        MainBlock mainBlock = mainBlockService.save(MainBlock.getDefaultMainBlock(subBlocks));
        Article savedArticle = articleRepository.save(Article.getDefaultArticle(loginMember, mainBlock));
        return savedArticle;
    }

    public Article save(Article article)
    {
        return articleRepository.save(article);
    }

    public Article getArticle(Long id) {
        // return articleRepository.getOne(id); // LazyLoading 때문에 json타입으로 변환할 때 에러 발생
        return articleRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public boolean isSameWriter(Long id, Member loginMember) {
        Article article = articleRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return article.isSameWriter(loginMember);
    }

    public List<Article> getArticles(Member loginMember) {
        return articleRepository.findAllByArticleInfo_Writer(loginMember);
    }

    public List<Article> getArticles(Member loginMember, int page) {
        PageRequest pageable = PageRequest.of(page, PageInfo.PAGE_BLOCK_SIZE);
        return articleRepository.findAllByArticleInfo_Writer(loginMember, pageable);
    }

    public PageInfo getArticlesPageInfo(Member loginMember, int currentPage) {
        int count = articleRepository.findAllByArticleInfo_Writer(loginMember).size();
        return new PageInfo(count, currentPage, PageInfo.PAGE_BLOCK_SIZE);
    }
}
