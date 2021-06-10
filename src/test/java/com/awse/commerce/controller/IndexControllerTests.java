package com.awse.commerce.controller;

import com.awse.commerce.domains.item.repository.ItemQueryRepository;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class IndexControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("index 페이지 조인시 상품목록 확인")
    @Test
    public void indexPageTest() throws Exception{
        log.info(mockMvc);
        mockMvc.perform(
                MockMvcRequestBuilders
                .get("/")
                )
                .andExpect(MockMvcResultMatchers.model().attributeExists("pageResult"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
