package com.awse.commerce.controller.like;

import com.awse.commerce.controller.member.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@Disabled
public class LikeApiControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("찜 추가하기 API 테스트")
    @Test
    @WithMockCustomUser
    public void addLikeAPITest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/like/15")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }
    @DisplayName("찜 삭제하기 API 테스트")
    @Test
    @WithMockCustomUser
    public void cancelLikeAPITest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/like/15")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

}
