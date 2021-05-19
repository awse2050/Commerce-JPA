package com.awse.commerce.domains.member.repository;

import com.awse.commerce.domains.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m, o from Member m left outer join Order o on o.orderer = m")
    List<Object[]> getMemberWithOrder();

    Optional<Member> findByEmail(String email);

    void deleteByEmail(String email);

    boolean existsByEmail(String email);
}
