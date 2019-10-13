package com.hardlearner.oia.service;

import com.hardlearner.oia.domain.Member;
import com.hardlearner.oia.dto.MemberLoginDto;
import com.hardlearner.oia.exception.MemberNotFoundException;
import com.hardlearner.oia.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    public Member login(Member member) {
        Member loginMember = memberRepository.findByEmail(member.getEmail()).orElseThrow(MemberNotFoundException::new);
        return loginMember;
    }

    public Member login(MemberLoginDto memberLoginDto) {
        Member loginMember = memberRepository.findByEmail(memberLoginDto.getEmail()).orElseThrow(MemberNotFoundException::new);
        if(loginMember.isValidPassword(memberLoginDto.getPassword())) {
            return loginMember;
        }
        throw new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다");
    }

    public Member save(Member joinMember) {
        return memberRepository.save(joinMember);
    }

    public boolean isValid(String email) {
        return !memberRepository.findByEmail(email).isPresent();
    }
}
