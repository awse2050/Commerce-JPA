package com.awse.commerce.domains.member.repository;

import com.awse.commerce.domains.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
