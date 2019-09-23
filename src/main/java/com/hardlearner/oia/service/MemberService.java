package com.hardlearner.oia.service;

import com.hardlearner.oia.domain.Member;
import com.hardlearner.oia.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    public Member login(Member member) {
        Member loginMember = memberRepository.findByEmail(member.getEmail()).orElseThrow(IllegalArgumentException::new);
        return loginMember;
    }
}
