package com.awse.commerce.service;

import com.awse.commerce.domains.like.service.LikeService;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.repository.MemberRepository;
import com.awse.commerce.domains.util.pagination.PageRequestDto;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Log4j2
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Transactional
public class LikeServiceTests {

    @Autowired
    private LikeService likeService;

    @Autowired
    private MemberRepository memberRepository;

    private final Long memberId = 1L;

    @DisplayName("좋아요 추가성공")
    @Test
    @Commit
    @Order(1)
    public void addLikeTests() {
        Member member = memberRepository.findById(memberId).get();

        likeService.addLike(member, 3L);

    }

    @DisplayName("좋아요 취소하기")
    @Test
    @Commit
    @Order(4)
    public void cancelLikeTests() {
        Member member = memberRepository.findById(memberId).get();

        likeService.cancelLike(member, 3L);
    }

    @DisplayName("페이징 + 찜 목록 조회 테스트")
    @Test
    @Order(3)
    public void findTest() {

        log.info(likeService.getMyLikeList(memberId, new PageRequestDto()).getDtoList());

        Assertions.assertThat(likeService.getMyLikeList(memberId, new PageRequestDto()).getDtoList().size()).isEqualTo(1);

    }
    @DisplayName("페이징 + 찜 목록 조회 실패 테스트")
    @Test
    @Order(5)
    public void notFindTest() {

        log.info(likeService.getMyLikeList(memberId, new PageRequestDto()).getDtoList());

        Assertions.assertThat(likeService.getMyLikeList(memberId, new PageRequestDto()).getDtoList().size()).isEqualTo(0);

    }

    @DisplayName("찜 여부 확인")
    @Test
    @Order(2)
    public void isEnabledLikeTest() {
        Member member = memberRepository.findById(memberId).get();

        boolean isLike = likeService.isEnabledLike(member, 3L);

        Assertions.assertThat(isLike).isTrue();
    }

}
