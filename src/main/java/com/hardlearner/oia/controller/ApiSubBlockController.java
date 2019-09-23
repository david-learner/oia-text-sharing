package com.hardlearner.oia.controller;

import com.hardlearner.oia.domain.ContentCategory;
import com.hardlearner.oia.domain.Pages;
import com.hardlearner.oia.domain.SubBlock;
import com.hardlearner.oia.service.MainBlockService;
import com.hardlearner.oia.service.SubBlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiSubBlockController {
    @Autowired
    SubBlockService subBlockService;

    @GetMapping("/api/subblocks/new")
    public SubBlock create() {
        return subBlockService.save(new SubBlock(new Pages(1, 2), true, ContentCategory.OBSERVATION, "SpringBoot로 개발하기"));
    }
}
