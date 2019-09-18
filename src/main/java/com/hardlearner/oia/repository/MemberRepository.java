package com.hardlearner.oia.repository;

import com.hardlearner.oia.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
