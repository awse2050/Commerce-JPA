package com.awse.commerce.controller.like;

import com.awse.commerce.controller.member.WithMockCustomUser;
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
public class LikeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("찜 목록 전체 조회")
    @WithMockCustomUser
    @Test
    public void getLikedList() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/mylike")

        ).andExpect(MockMvcResultMatchers.model().attributeExists("pageResult"))
                .andDo(MockMvcResultHandlers.print());
    }
}
