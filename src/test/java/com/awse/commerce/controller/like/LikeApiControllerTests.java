package com.awse.commerce.controller.like;

import com.awse.commerce.controller.member.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class LikeApiControllerTests {

    @Autowired
    private MockMvc mockMvc;

    // local - 1, EC2 - 102
    private final String itemId = "102";

    @DisplayName("찜 추가하기 API 테스트")
    @Test
    @WithMockCustomUser
    @Order(1)
    public void addLikeAPITest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/api/like/"+itemId)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @DisplayName("찜 삭제하기 API 테스트")
    @Test
    @WithMockCustomUser
    @Order(2)
    public void cancelLikeAPITest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/like/"+itemId)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

}
