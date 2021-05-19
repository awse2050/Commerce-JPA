package com.awse.commerce.service;

import com.awse.commerce.domains.member.dto.SignUpRequest;
import com.awse.commerce.domains.member.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Log4j2
@Transactional
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;

    @DisplayName("회원가입 테스트")
    @Test
    @Commit
    public void signUpTest() {

        SignUpRequest dto = SignUpRequest.builder()
                .email("kyh3964@naver.com")
                .name("김윤환")
                .password("11111111")
                .confirmPassword("11111112")
                .zipcode("14954")
                .extraAddress("경기도 시흥시 은계남로 11")
                .detailsAddress("904-1006")
                .build();

       Long result = memberService.signUp(dto);
       Assertions.assertThat(result).isNotNull();
    }

}
