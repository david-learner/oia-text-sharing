package com.hardlearner.oia.controller;

import com.hardlearner.oia.domain.Member;
import com.hardlearner.oia.security.HttpSessionUtils;
import com.hardlearner.oia.service.ArticleService;
import com.hardlearner.oia.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    private MemberService memberService;
    private ArticleService articleService;

    @Autowired
    public HomeController(MemberService memberService, ArticleService articleService) {
        this.memberService = memberService;
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/main")
    public ModelAndView main(@RequestParam int page, ModelAndView mav, HttpSession session) {
        Member loginMember = HttpSessionUtils.getMemberFromSession(session);
        if (loginMember == null) {
            return new ModelAndView("redirect:/");
        }
        mav.setViewName("index");
        mav.addObject("articles", articleService.getArticles(loginMember, page));
        mav.addObject("pages", articleService.getArticlesPages(loginMember, page));
        mav.addObject("loginMember", loginMember);
        return mav;
    }

    @GetMapping("login")
    public String loginView() {
        return "login";
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        session.setAttribute("loginMember", memberService.login(email, password));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("logout")
    public ResponseEntity logout(HttpSession session) {
        session.removeAttribute("loginMember");
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
