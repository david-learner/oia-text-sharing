package com.hardlearner.oia.controller;

import com.hardlearner.oia.domain.JoinDto;
import com.hardlearner.oia.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MemberController {
    private static final Logger log =  LoggerFactory.getLogger(MemberController.class);

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/join")
    public String joinView() {
        return "join";
    }

    @PostMapping("/members/join")
    public ResponseEntity join(@RequestBody JoinDto joinDto) {
        log.debug("joinDto: " + joinDto.toString());
        memberService.save(joinDto.toMember());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/members/valid/email")
    public ResponseEntity isValidEmail(String email) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.isValid(email));
    }
}
