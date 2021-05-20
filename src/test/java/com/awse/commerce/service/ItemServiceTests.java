package com.awse.commerce.service;

import com.awse.commerce.domains.item.dto.ItemDetailsDto;
import com.awse.commerce.domains.item.dto.ItemRequestDto;
import com.awse.commerce.domains.item.service.ItemService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class ItemServiceTests {

    @Autowired
    private ItemService itemService;

    @DisplayName("상품등록 테스트")
    @Test
    @Transactional
    @Commit
    public void itemSaveTest() {
        ItemRequestDto dto = ItemRequestDto.builder()
                .itemAmount(1300)
                .imgPath("none")
                .itemName("연필")
                .stockQuantity(11)
                .build();

        Long result = itemService.saveItem(dto);

        Assertions.assertThat(result).isNotEqualTo(null);
    }

    @DisplayName("상품 조회 테스트")
    @Transactional
    @Test
    public void findItemTest() {
        ItemDetailsDto dto = itemService.findItem(21L);

        Assertions.assertThat(dto).isNotNull();
    }

}
