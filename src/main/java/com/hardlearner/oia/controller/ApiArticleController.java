package com.hardlearner.oia.controller;

import com.hardlearner.oia.domain.Article;
import com.hardlearner.oia.domain.ShareLinkUtils;
import com.hardlearner.oia.dto.ArticleDto;
import com.hardlearner.oia.domain.Member;
import com.hardlearner.oia.exception.AuthenticationException;
import com.hardlearner.oia.security.LoginMember;
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
    private static final Logger log = LoggerFactory.getLogger(ApiArticleController.class);

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
    public ResponseEntity save(@PathVariable Long id, @RequestBody ArticleDto articleDto, @LoginMember Member member) {
        Article article = articleService.getArticle(articleDto.getId());
        article.update(articleDto);
        articleService.save(article);
        return ResponseEntity.status(HttpStatus.OK).body("OKAY");
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable Long id, @LoginMember Member loginMember, @VerifiedEmail String email) {
        log.debug("EMAIL : {}", email);
        if (!articleService.isSameWriter(id, loginMember)) {
            throw new AuthenticationException();
        }
        Article article = articleService.getArticle(id);
        return article;
    }

    @GetMapping("/members/{id}")
    public List<Article> getArticles(@PathVariable Long id, @LoginMember Member loginMember) {
        return articleService.getArticles(loginMember);
    }

    @GetMapping("/{id}/share")
    public Article getShareAllowedArticle(@PathVariable Long id) {
        return articleService.getShareAllowedArticle(id);
    }

    @PostMapping("/{id}/share")
    public String getArticleShareLink(@LoginMember Member member, HttpServletRequest request) {
        return ShareLinkUtils.generate(request.getRequestURI());
    }
}
