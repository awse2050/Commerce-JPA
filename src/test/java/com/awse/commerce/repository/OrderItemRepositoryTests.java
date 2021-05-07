package com.awse.commerce.repository;

import com.awse.commerce.domains.item.entity.Item;
import com.awse.commerce.domains.order.entity.OrderItem;
import com.awse.commerce.domains.order.repository.OrderItemRepository;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Log4j2
public class OrderItemRepositoryTests {

    @Autowired
    private OrderItemRepository orderItemRepository;

    // 주문과 동시에 이루어지는 작업
    // 테스트시 동시에 이루어진다 가정 하에
    // 로직상 먼저 추가되므로 먼저 진행.
    @DisplayName("주문상품 추가")
    @Test
    public void insertOrderItemTest() {
        Item item = Item.builder().itemId(2L).build();

        OrderItem orderItem = OrderItem.builder()
                .orderCount(1) // 주문 상품의 개수를 파악
                .orderItemAmount(2000) // 상품의 각 가격을 합침
                .item(item)
                .build();

        orderItemRepository.save(orderItem);

        Assertions.assertThat(orderItemRepository.count()).isEqualTo(1);
    }

    @DisplayName("조회")
    @Test
    public void findTest() {
        orderItemRepository.findAll().forEach(ol -> {
            log.info(ol);
            log.info(ol.getItem());
        });
    }

    @Transactional
    @DisplayName("join Fetch 테스트")
    @Test
    public void joinFetchTest() {
        orderItemRepository.findAllJoinFetch().forEach(i -> {
            log.info(i);
            log.info(i.getItem());
        });
    }
}
