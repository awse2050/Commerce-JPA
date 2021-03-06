package com.awse.commerce.controller.member;

import com.awse.commerce.domains.cart.entity.Cart;
import com.awse.commerce.domains.cart.repository.CartRepository;
import com.awse.commerce.domains.member.dto.ModifyMemberDto;
import com.awse.commerce.domains.member.dto.ModifyPasswordDto;
import com.awse.commerce.domains.member.dto.SignUpRequest;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders; // 요청데이터 설정
import org.springframework.test.web.servlet.result.MockMvcResultHandlers; // 실행결과 출력

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
    private CartRepository cartRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final static String API_URI = "/api/member";

    @DisplayName("회원가입하기 - 성공")
    @Test
    @Disabled
    public void signUpTest() throws Exception{
        SignUpRequest dto = getSignUpRequest();

        mockMvc.perform(
                MockMvcRequestBuilders
                .post(API_URI)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
                .andExpect(status().isOk());

        Optional<Member> list = memberRepository.findByEmail("aaa334@naver.com");

        Cart cart = cartRepository.findByMemberId(list.get().getId()).get();

        Assertions.assertThat(list.isPresent()).isEqualTo(true);
        Assertions.assertThat(cart).isNotNull();
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

    @DisplayName("회원정보 수정")
    @WithMockCustomUser // 2 - user1@aaa.com
    @Test
    public void modifyMemberTest() throws Exception {
        ModifyMemberDto dto = getModifyMemberDto();

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put(API_URI)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().is2xxSuccessful()) // 테스트시 원하는 기대값
                .andDo(MockMvcResultHandlers.print()); // 테스트 이후 실행
    }

    @DisplayName("패스워드 수정")
    @WithMockCustomUser // 2 - user1@aaa.com
    @Test
    @Disabled
    public void modifyPasswordTest() throws Exception {

        ModifyPasswordDto dto = getPasswordDto();

        mockMvc.perform(
                MockMvcRequestBuilders
                        .patch(API_URI)
                        .content(objectMapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().is4xxClientError()) // 테스트시 원하는 기대값
                .andDo(MockMvcResultHandlers.print()); // 테스트 이후 실행
    }


    private SignUpRequest getSignUpRequest() {
        return SignUpRequest.builder()
                .email("user1@aaa.com")
                .name("김모씨")
                .password("111111111")
                .confirmPassword("111111111")
                .phone("01033229483")
                .zipcode("14954")
                .extraAddress("경기도 시흥시 은계남로 11")
                .detailsAddress("904-1006")
                .build();
    }

    private ModifyMemberDto getModifyMemberDto() {
        return ModifyMemberDto.builder()
                .name("김씨")
                .email("dfkdk@naver.com")
                .phone("01031943333")
                .zipcode("19483")
                .extraAddress("허허허허")
                .detailsAddress("하하호")
                .build();

    }

    private ModifyPasswordDto getPasswordDto() {
        return ModifyPasswordDto.builder()
                .currentPassword("20202020")
                .toModifyPassword("20202020")
                .confirmPassword("20202020")
                .build();
    }
}
