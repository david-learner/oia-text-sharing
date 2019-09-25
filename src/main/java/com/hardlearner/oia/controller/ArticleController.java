package com.hardlearner.oia.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hardlearner.oia.domain.Article;
import com.hardlearner.oia.domain.Member;
import com.hardlearner.oia.repository.MemberRepository;
import com.hardlearner.oia.service.ArticleService;
import com.hardlearner.oia.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Controller
public class ArticleController {
    private static final Logger log =  LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    ArticleService articleService;
    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/articles/new")
    public String createArticle(Member member) {
//        return "articleForm";
        memberRepository.save(Member.guest);
        Article savedArticle = articleService.create(Member.guest);
        return "redirect:" + "/articles/" + savedArticle.getId();
    }

    @GetMapping("/articles/{id}")
    public ModelAndView getArticle(@PathVariable Long id, Member member) throws JsonProcessingException {
        Article savedArticle = articleService.getArticle(id);

        // Java Object to JSON string
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String json = om.writeValueAsString(savedArticle);

        // 왜 굳이 JSON으로 내려보내나?
        // Handlebars로 FE에서 데이터를 보여줄 수 있지만 특정 조건에 따라 Helper를 만들어야 하기 때문에 불편
        // JSON으로 받아서 js로 데이터를 보여주는 게 편하다고 생각
        ModelAndView mav = new ModelAndView("articleForm");
        mav.addObject("article", json);

        return mav;
    }
}
