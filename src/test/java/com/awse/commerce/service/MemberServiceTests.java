package com.awse.commerce.service;

import com.awse.commerce.domains.member.dto.SignUpRequest;
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
@Disabled
public class MemberServiceTests {

    @Autowired
    private MemberService memberService;

    @DisplayName("회원가입 테스트")
    @Test
    @Commit
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
}
