package com.awse.commerce.controller.member;

import com.awse.commerce.domains.member.dto.SignUpRequest;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders; // 요청데이터 설정
import org.springframework.test.web.servlet.result.MockMvcResultHandlers; // 실행결과 출력
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; // 실행결과를 검증

@SpringBootTest
@AutoConfigureMockMvc
public class MemberApiControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final static String API_URI = "/api/member";

    @Test
    @Commit
    @Transactional
    public void delete() {
        memberRepository.deleteByEmail("aaa33@naver.com");
    }

    @DisplayName("회원가입하기 - 성공")
    @Test
    public void signUpTest() throws Exception{
        SignUpRequest dto = getSignUpRequest();

        mockMvc.perform(
                MockMvcRequestBuilders
                .post(API_URI)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
                .andExpect(status().isOk());

        Optional<Member> list = memberRepository.findByEmail("aaa33@naver.com");

        Assertions.assertThat(list.isPresent()).isEqualTo(true);
    }

    @DisplayName("회원가입하기 - 실패(비밀번호 불일치)")
    @Test
    public void signUpTestFail() throws Exception {
        SignUpRequest dto = getSignUpRequest();

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post(API_URI)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isBadRequest()) // 테스트시 원하는 기대값
                .andDo(MockMvcResultHandlers.print()); // 테스트 이후 실행

    }

    @DisplayName("회원가입하기 - 실패(이메일 중복)")
    @Test
    public void signUpTestFail2() throws Exception {
        SignUpRequest dto = getSignUpRequest();

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post(API_URI)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isBadRequest()) // 테스트시 원하는 기대값
                .andDo(MockMvcResultHandlers.print()); // 테스트 이후 실행

    }

    private SignUpRequest getSignUpRequest() {
        return SignUpRequest.builder()
                .email("aaa33@naver.com")
                .name("김모씨")
                .password("111111111")
                .confirmPassword("111111111")
                .zipcode("14954")
                .extraAddress("경기도 시흥시 은계남로 11")
                .detailsAddress("904-1006")
                .build();
    }
}