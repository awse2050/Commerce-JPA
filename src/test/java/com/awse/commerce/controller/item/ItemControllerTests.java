package com.awse.commerce.controller.item;

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
public class ItemControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("상품 조회 테스트")
    @Test
    public void findItemWithItemIdTest() throws Exception {

        String itemId = "1";

        mockMvc.perform(MockMvcRequestBuilders.get("/item/"+itemId))
                .andExpect(MockMvcResultMatchers.model().attributeExists("item"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("item/itemDetails"))
                .andDo(MockMvcResultHandlers.print());

    }

}
