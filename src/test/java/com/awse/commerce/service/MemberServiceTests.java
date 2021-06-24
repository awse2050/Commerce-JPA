package com.awse.commerce.service;

import com.awse.commerce.domains.cart.repository.CartRepository;
import com.awse.commerce.domains.member.dto.ModifyMemberDto;
import com.awse.commerce.domains.member.dto.ModifyPasswordDto;
import com.awse.commerce.domains.member.dto.SignUpRequest;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.exception.MemberBadRequestException;
import com.awse.commerce.domains.member.repository.MemberRepository;
import com.awse.commerce.domains.member.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Log4j2
@Transactional
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PasswordEncoder encoder;

    @DisplayName("회원가입 테스트")
    @Test
    @Commit
    @Order(1)
    public void signUpTest2() {
        Long result = null;

        SignUpRequest dto = SignUpRequest.builder()
                .email("test555@aaa.com")
                .name("사용자")
                .password("11111111")
                .confirmPassword("11111111")
                .phone("01098389923")
                .zipcode("14954")
                .extraAddress("경기도 시흥시 은계남로 1")
                .detailsAddress("904-1006")
                .build();

        result = memberService.signUp(dto);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(memberRepository.findByEmail("test555@aaa.com").isPresent()).isTrue();
    }

    @DisplayName("회원 수정하기")
    @Test
    @Commit
    @Order(2)
    public void modifyMemberTest() {

        ModifyMemberDto memberDto = ModifyMemberDto.builder()
                .name("하하44")
                .email("test555@aaa.com")
                .phone("01031943333")
                .zipcode("19483")
                .extraAddress("허허허허")
                .detailsAddress("하하호")
                .build();

        Long memberId = memberRepository.findByEmail("test555@aaa.com").get().getId();

        memberService.modifyMemberInfo(memberId, memberDto);

        Member member = memberRepository.findById(memberId).get();

        Assertions.assertThat(member.getName()).isEqualTo(memberDto.getName());
        Assertions.assertThat(member.getPhone()).isEqualTo(memberDto.getPhone());

    }

    @DisplayName("패스워드 변경")
    @Test
    @Commit
    @Order(3)
    public void modifyPassword() {
        ModifyPasswordDto dto = new ModifyPasswordDto("11111111", "20202020", "20202020" );

        Long memberId = memberRepository.findByEmail("test555@aaa.com").get().getId();

        memberService.changePassword(memberId, dto);

        Member member = memberRepository.findById(memberId).get();

        Assertions.assertThat(encoder.matches(dto.getToModifyPassword(), member.getPassword())).isTrue();
    }

    @DisplayName("회원 삭제 - 임시로 Repository 사용")
    @Test
    @Commit
    @Order(4)
    public void deleteMemberTest() {
       Member member = memberRepository.findByEmail("test555@aaa.com").
               orElseThrow(() -> new MemberBadRequestException());
       // 장바구니가 생성되어있음.
       cartRepository.deleteByMemberId(member.getId());

       memberRepository.deleteById(member.getId());

       Assertions.assertThat(memberRepository.findById(member.getId()).isPresent()).isFalse();
    }
}
