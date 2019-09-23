package com.hardlearner.oia.controller;

import com.hardlearner.oia.domain.Article;
import com.hardlearner.oia.domain.ArticleDto;
import com.hardlearner.oia.domain.Member;
import com.hardlearner.oia.service.ArticleService;
import com.hardlearner.oia.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class ApiArticleController {
    private static final Logger log = LoggerFactory.getLogger(ApiArticleController.class);

    @Autowired
    MemberService memberService;
    @Autowired
    ArticleService articleService;

    @GetMapping("/api/articles/new")
    public Article create() {
        Member guest = memberService.login(Member.guest);
        return articleService.create(guest);
    }

    @PostMapping("/api/articles/save")
    public Article save(@RequestBody ArticleDto articleDto, HttpSession session) {
        Member guest = memberService.login(Member.guest);
        log.debug("articleDto is " + articleDto.toString());
        log.debug("guest is " + guest.toString());
        return null;
    }
}
