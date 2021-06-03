package com.awse.commerce.domains.like.repository;

import com.awse.commerce.domains.item.entity.Item;
import com.awse.commerce.domains.like.entity.Like;
import com.awse.commerce.domains.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // 삭제를 위해 사용자와 상품으로 like 조회
    Optional<Like> findByMemberAndItem(Member member, Item item);
}
