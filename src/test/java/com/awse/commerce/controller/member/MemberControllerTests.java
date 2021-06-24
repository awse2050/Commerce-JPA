package com.awse.commerce.controller.member;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@Log4j2
public class MemberControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("로그인 페이지")
    @Test
    public void getLoginForm() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @DisplayName("회원가입 페이지")
    @Test
    public void getSignUp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/signup"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("마이페이지")
    @WithMockCustomUser
    @Test
    public void getMyPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/mypage")
        )
                .andExpect(MockMvcResultMatchers.model().attributeExists("cart"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("orderList"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("like"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }
}