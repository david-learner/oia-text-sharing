package com.hardlearner.oia.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArticleController {
    @GetMapping("/article/new")
    public String createArticle() {
        return "articleForm";
    }
}
