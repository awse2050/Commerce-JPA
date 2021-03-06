package com.awse.commerce.repository;

import com.awse.commerce.domains.item.entity.Item;
import com.awse.commerce.domains.item.repository.ItemRepository;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Log4j2
public class ItemRepositoryTests {

    @Autowired
    private ItemRepository itemRepository;

    // local - 1L , EC2 - 102L
    private final Long itemId = 102L;

    @DisplayName("상품 목록 추가")
    @Test
    @Disabled
    public void insertItemsTest() {
        String[] strArr = {"연필", "지우개", "샤프심"};
        for(int i = 0; i < 20; i++) {
            int r = (int)(Math.random() * 3);
            Item item = Item.builder()
                    .imgPath("none")
                    .name(strArr[r] + i)
                    .money(r==0 ? 2000 : r == 1 ? 500 : 300)
                    .stockQuantity(r+i)
                    .build();

            itemRepository.save(item);
        }

        Assertions.assertThat(itemRepository.count()).isEqualTo(20);
    }

    @DisplayName("상품 조회 및 좋아요 확인")
    @Transactional
    @Test
    public void findTests() {
        Item item = itemRepository.findById(itemId).get();
        log.info(item);

        item.getLikes().forEach(i -> log.info(i));

        Assertions.assertThat(item).isNotNull();
        Assertions.assertThat(item.getLikes()).isNotNull();
    }
}
