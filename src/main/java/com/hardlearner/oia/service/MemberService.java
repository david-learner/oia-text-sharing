package com.hardlearner.oia.service;

import com.hardlearner.oia.domain.Member;
import com.hardlearner.oia.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    public Member login(Member member) {
        Member loginMember = memberRepository.findByEmail(member.getEmail()).orElseThrow(IllegalArgumentException::new);
        return loginMember;
    }

    public Member login(String email, String password) {
        Member loginMember = memberRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);
        if(loginMember.isValidPassword(password)) {
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
