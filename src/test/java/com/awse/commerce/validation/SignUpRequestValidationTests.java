package com.awse.commerce.validation;

import com.awse.commerce.domains.member.dto.SignUpRequest;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.Validator;

@SpringBootTest
@Log4j2
public class SignUpRequestValidationTests {

    @Autowired
    private Validator validator;

    @DisplayName("회원등록 DTO Validation 테스트")
    @Test
    public void SignUpDtoValidTest() {
        SignUpRequest sign = SignUpRequest.builder()
                .email("user1212@naver.com")
                .name("사용자1")
                .password("pw2211111")
                .confirmPassword("pw2211111")
                .zipcode("12223")
                .extraAddress("경기도 시흥시")
                .detailsAddress("909-193")
                .build();
        // 검증결과 미통과한 부분에 대한 메세지가 출력된다.
        validator.validate(sign).stream().forEach(i -> log.info(i.getMessage()));
        Assertions.assertThat(validator.validate(sign).size()).isEqualTo(1);
    }

}
