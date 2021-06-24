package com.awse.commerce.repository.querydsl;

import com.awse.commerce.domains.order.entity.Order;
import com.awse.commerce.domains.order.repository.OrderQueryRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Log4j2
public class OrderQueryRepositoryTests {

    @Autowired
    private OrderQueryRepository orderQueryRepository;

    @DisplayName("페이징 + 주문내역전체조회")
    @Transactional
    @Test
    public void getMyOrderListWithPagingTest() {
        Page<Order> pageList =
                orderQueryRepository.
                        getMyOrders(12L, PageRequest.of(0,10, Sort.by("orderId").descending()),null);

        log.info(pageList);
        pageList.stream().forEach(page -> {
            page.getOrderItemList().stream().forEach(i -> {
                log.info(i.getOrderItemId());
                log.info(i.getItem().getItemId());
                log.info(i.getItem().getName());
            });
        });

    }

}
