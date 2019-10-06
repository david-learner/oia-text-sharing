package com.hardlearner.oia.controller;

import com.hardlearner.oia.domain.Member;
import com.hardlearner.oia.security.LoginMember;
import com.hardlearner.oia.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    private MemberService memberService;

    @Autowired
    public HomeController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("login")
    public String loginView() {
        return "login";
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        Member member = memberService.login(email, password);
        session.setAttribute("loginMember", member);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("logout")
    public ResponseEntity logout(HttpSession session) {
        session.removeAttribute("loginMember");
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
