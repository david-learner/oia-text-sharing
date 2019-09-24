package com.hardlearner.oia.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hardlearner.oia.domain.Article;
import com.hardlearner.oia.domain.Member;
import com.hardlearner.oia.repository.MemberRepository;
import com.hardlearner.oia.service.ArticleService;
import com.hardlearner.oia.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ArticleController {
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
        ModelAndView mav = new ModelAndView("articleForm");
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(savedArticle);
        mav.addObject("article", json);
        return mav;
    }
}
