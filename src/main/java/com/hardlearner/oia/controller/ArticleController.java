package com.hardlearner.oia.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hardlearner.oia.domain.Article;
import com.hardlearner.oia.domain.Member;
import com.hardlearner.oia.repository.MemberRepository;
import com.hardlearner.oia.security.LoginMember;
import com.hardlearner.oia.service.ArticleService;
import com.hardlearner.oia.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Controller
public class ArticleController {
    private static final Logger log =  LoggerFactory.getLogger(ArticleController.class);

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
//        Article savedArticle = articleService.create(memberService.login(Member.guest));
        Article savedArticle = articleService.create(memberService.login(loginMember));
        return ResponseEntity.status(HttpStatus.CREATED).location(new URI("/articles/"+savedArticle.getId())).build();
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Member member) {
        // member check
        // service단에서 발생하는 exception 잡아서 FE로 보내고 alert 발생시키기 그리고 redirect-backward
        return "articleForm";
    }
}
