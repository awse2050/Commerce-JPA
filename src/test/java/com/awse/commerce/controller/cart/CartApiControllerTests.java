package com.awse.commerce.controller.cart;

import com.awse.commerce.controller.member.WithMockCustomUser;
import com.awse.commerce.domains.cart.dao.AddRequestItemDao;
import com.awse.commerce.domains.cart.dao.RemoveItemDao;
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
public class CartApiControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // local - 1L, EC2 - 102L
    private final Long itemId = 102L;

    @DisplayName("장바구니 담기 유효성 검사 - 결과(성공)")
    @WithMockCustomUser
    @Test
    @Order(1)
    public void validateTestAddToCart2() throws Exception {

        AddRequestItemDao addRequestItemDao = new AddRequestItemDao(itemId, 1);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/cart")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(addRequestItemDao))
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @DisplayName("장바구니 상품 한개 삭제하기(버튼식) - 성공")
    @WithMockCustomUser
    @Test
    @Disabled
    public void removeItemInCartSuccessTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/cart/" + itemId)
                .contentType("application/json")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @DisplayName("선택삭제 테스트 - 성공")
    @WithMockCustomUser
    @Test
    @Order(2)
    public void selectRemoveItemTest() throws Exception {

        RemoveItemDao dao = new RemoveItemDao();
        dao.getItemIdList().add(itemId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/cart")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dao))
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("전체삭제 테스트 - 성공")
    @WithMockCustomUser
    @Test
    @Disabled
    public void allRemoveItemTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/cart/all")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}
