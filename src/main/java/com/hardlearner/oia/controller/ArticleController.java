package com.hardlearner.oia.controller;

import com.hardlearner.oia.domain.Article;
import com.hardlearner.oia.domain.Member;
import com.hardlearner.oia.exception.AuthenticationException;
import com.hardlearner.oia.security.LoginMember;
import com.hardlearner.oia.service.ArticleService;
import com.hardlearner.oia.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class ArticleController {
    private static final Logger log = LoggerFactory.getLogger(ArticleController.class);

    private ArticleService articleService;
    private MemberService memberService;

    @Autowired
    public ArticleController(ArticleService articleService, MemberService memberService) {
        this.articleService = articleService;
        this.memberService = memberService;
    }

    // /api/articles/new랑 중복
    @PostMapping("/articles/new")
    public ResponseEntity createArticle(@LoginMember Member loginMember) throws URISyntaxException {
        log.debug("loginUser : {}", loginMember.toString());
        Article savedArticle = articleService.create(memberService.login(loginMember));
        return ResponseEntity.status(HttpStatus.CREATED).location(new URI("/articles/" + savedArticle.getId())).build();
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, @LoginMember Member loginMember) {
        if(!articleService.isSameWriter(id, loginMember)) {
            throw new AuthenticationException();
        }
        return "articleForm";
    }
}
