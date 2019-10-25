package com.hardlearner.oia.controller;

import com.hardlearner.oia.domain.Article;
import com.hardlearner.oia.domain.Member;
import com.hardlearner.oia.domain.ShareLinkUtils;
import com.hardlearner.oia.dto.ArticleDto;
import com.hardlearner.oia.exception.ArticleNotFoundException;
import com.hardlearner.oia.exception.AuthenticationException;
import com.hardlearner.oia.security.VerifiedEmail;
import com.hardlearner.oia.service.ArticleService;
import com.hardlearner.oia.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ApiArticleController {
    private MemberService memberService;
    private ArticleService articleService;

    @Autowired
    public ApiArticleController(MemberService memberService, ArticleService articleService) {
        this.memberService = memberService;
        this.articleService = articleService;
    }

    @PostMapping
    public Article create() {
        Member guest = memberService.login(Member.GUEST_MEMBER);
        return articleService.create(guest);
    }

    @PostMapping("/{id}/save")
    public ResponseEntity save(@PathVariable Long id, @RequestBody ArticleDto articleDto, @VerifiedEmail String email) {
        Article article = articleService.getArticle(articleDto.getId());
        article.update(articleDto);
        articleService.save(article);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable Long id, @VerifiedEmail String email) {
        if (!articleService.isSameWriter(id, email)) {
            throw new AuthenticationException();
        }
        Article article = articleService.getArticle(id);
        return article;
    }

    @GetMapping("/members/{id}")
    public List<Article> getArticles(@PathVariable Long id, @VerifiedEmail String email) {
        return articleService.getArticles(id);
    }

    @GetMapping("/{id}/share")
    public Article getShareAllowedArticle(@PathVariable Long id, @RequestParam String key, HttpServletRequest request) {
        // /api/articles/~ -> /articles/~
        String notApiUrl = request.getRequestURI().substring(4);
        if (!ShareLinkUtils.authorize(notApiUrl, key)) {
            throw new ArticleNotFoundException();
        }
        return articleService.getShareAllowedArticle(id);
    }

    @PostMapping("/{id}/share")
    public String getArticleShareLink(@VerifiedEmail String email, HttpServletRequest request) {
        return ShareLinkUtils.generate(request.getRequestURI());
    }
}
