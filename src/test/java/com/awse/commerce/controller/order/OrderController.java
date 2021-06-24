package com.awse.commerce.controller.order;

import com.awse.commerce.controller.member.WithMockCustomUser;
import com.awse.commerce.domains.cart.dao.CheckoutDao;
import com.awse.commerce.domains.cart.dto.CheckoutDaoListDto;
import com.awse.commerce.domains.util.pagination.PageRequestDto;
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

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("체크아웃")
    @Test
    @Disabled
    public void checkoutTest() throws Exception{

        List<CheckoutDao> daoList = new ArrayList<>();
        daoList.add(new CheckoutDao(2L, 1));
        daoList.add(new CheckoutDao(8L, 1));

        CheckoutDaoListDto listdto = new CheckoutDaoListDto(daoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/checkout")
        .content(objectMapper.writeValueAsString(listdto))
        .contentType("application/json")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @DisplayName("페이징 + 주문내역조회")
    @Test
    @WithMockCustomUser
    public void getOrderListWithPaging() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/order/order_list")
        ).andExpect(MockMvcResultMatchers.model().attributeExists("pageResult"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}

