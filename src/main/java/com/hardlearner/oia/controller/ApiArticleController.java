package com.hardlearner.oia.controller;

import com.hardlearner.oia.domain.Article;
import com.hardlearner.oia.domain.ArticleDto;
import com.hardlearner.oia.domain.Member;
import com.hardlearner.oia.service.ArticleService;
import com.hardlearner.oia.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiArticleController {
    private static final Logger log = LoggerFactory.getLogger(ApiArticleController.class);

    @Autowired
    MemberService memberService;
    @Autowired
    ArticleService articleService;

    @PostMapping("/api/articles/new")
    public Article create() {
        Member guest = memberService.login(Member.guest);
        return articleService.create(guest);
    }

    // save is update
    @PostMapping("/api/articles/save")
    public Article save(@RequestBody ArticleDto articleDto) {
        // session에 있는 멤버 정보 가져와서 article 생성할 때 같이 넣어주기
        Article article = articleService.getArticle(articleDto.getId());
        article.update(articleDto);
        Member loginMember = memberService.login(Member.guest);
        articleService.save(article);
        log.debug("article is " + article.toString());
        return null;
    }

    @GetMapping("/api/articles/{id}")
    public Article load(@PathVariable Long id) {
        Article article = articleService.getArticle(id);
        return article;
    }
}
