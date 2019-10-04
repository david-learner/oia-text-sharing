package com.hardlearner.oia.controller;

import com.hardlearner.oia.domain.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("index")
    public ModelAndView index(HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        session.setAttribute("loginMember", loginMember);

        ModelAndView mov = new ModelAndView("index");
        mov.addObject("loginMember", loginMember);

        return mov;
    }
}
