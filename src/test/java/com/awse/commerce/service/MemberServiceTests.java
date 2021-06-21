package com.awse.commerce.service;

import com.awse.commerce.domains.member.dto.ModifyMemberDto;
import com.awse.commerce.domains.member.dto.SignUpRequest;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.repository.MemberRepository;
import com.awse.commerce.domains.member.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Log4j2
@Transactional
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder encoder;

    @DisplayName("회원가입 테스트")
    @Test
    @Commit
    @Disabled
    public void signUpTest2() {
        Long result = null;
        for(int i=1; i < 30; i++) {

            SignUpRequest dto = SignUpRequest.builder()
                    .email("user"+i+"@aaa.com")
                    .name("사용자"+i)
                    .password("11111111")
                    .confirmPassword("11111111")
                    .phone("01098389923")
                    .zipcode("14954")
                    .extraAddress("경기도 시흥시 은계남로 1"+i)
                    .detailsAddress("904-1006")
                    .build();

            result = memberService.signUp(dto);
        }
        Assertions.assertThat(result).isNotNull();
    }

    @DisplayName("회원 수정하기 - 패스워드 정상변경.")
    @Test
    @Commit
    public void modifyMemberTest() {

        ModifyMemberDto memberDto = ModifyMemberDto.builder()
                .name("하하44")
                .email("dfkdk@naver.com")
                .phone("01031943333")
                .zipcode("19483")
                .extraAddress("허허허허")
                .detailsAddress("하하호")
                .build();

        Long memberId = 5L;

        memberService.modifyMemberInfo(memberId, memberDto);

        Member member = memberRepository.findById(memberId).get();

        log.info(member);

        boolean result = encoder.matches("20202020", member.getPassword() );
        // 바뀌었다면
        Assertions.assertThat(result).isTrue();

    }
}
