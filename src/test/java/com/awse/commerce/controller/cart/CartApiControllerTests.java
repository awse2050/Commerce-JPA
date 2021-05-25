package com.awse.commerce.controller.cart;

import com.awse.commerce.controller.member.WithMockCustomUser;
import com.awse.commerce.domains.cart.dao.AddRequestItemDao;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class CartApiControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("장바구니 담기 유효성 검사 - 결과(실패)")
    @WithMockCustomUser
    @Test
    public void validateTestAddToCart() throws Exception{

        AddRequestItemDao addRequestItemDao = new AddRequestItemDao(120L, 0);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/cart")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(addRequestItemDao))
        )
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());

    }

    @DisplayName("장바구니 담기 유효성 검사 - 결과(성공)")
    @WithMockCustomUser
    @Test
    public void validateTestAddToCart2() throws Exception{

        AddRequestItemDao addRequestItemDao = new AddRequestItemDao(112L, 1);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/cart")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(addRequestItemDao))
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

}