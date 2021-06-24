package com.awse.commerce.repository;

import com.awse.commerce.domains.item.entity.Item;
import com.awse.commerce.domains.item.repository.ItemRepository;
import com.awse.commerce.domains.like.entity.Like;
import com.awse.commerce.domains.like.repository.LikeRepository;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.repository.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Log4j2
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class LikeRepositoryTests {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    // local - 4L , EC2 - 104L
    private final Long itemId = 104L;

    @DisplayName("좋아요 추가")
    @Test
    @Commit
    @Transactional
    @Order(1)
    public void addLikeTest() {
        // 사용자 하나 찾고
        Member member = memberRepository.findById(3L).get();
        // 아이템 하나 찾고
        Item item = itemRepository.findById(itemId).get();
        // 추가하기
        Like like = new Like(member,item);

        Long likeId = likeRepository.save(like).getId();

        Assertions.assertThat(likeId).isNotNull();

    }

    @DisplayName("좋아요 찾기")
    @Transactional
    @Test
    @Order(2)
    public void findByMemberAndItemTest() {
        // 사용자 하나 찾고
        Member member = memberRepository.findById(3L).get();
        // 아이템 하나 찾고
        Item item = itemRepository.findById(itemId).get();

        Optional<Like> like = likeRepository.findByMemberAndItem(member, item);

        log.info(like.isPresent());
        Assertions.assertThat(like.isPresent()).isTrue();
    }

    @DisplayName("좋아요 삭제(취소)")
    @Test
    @Commit
    @Transactional
    @Order(3)
    public void cancelLikeTest() {
        // 사용자 하나 찾고
        Member member = memberRepository.findById(3L).get();
        // 아이템 하나 찾고
        Item item = itemRepository.findById(itemId).get();

        Like like = likeRepository.findByMemberAndItem(member, item).get();

        likeRepository.delete(like);

    }

}
