package com.awse.commerce.controller.cart;

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
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Disabled
public class CartControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("장바구니 조회")
    @WithMockCustomUser
    @Test
    @Transactional
    public void findCart() throws Exception {

        Long memberId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/cart/user/"+memberId)
        )
                .andExpect(MockMvcResultMatchers.model().attributeExists("cartList"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }
}
