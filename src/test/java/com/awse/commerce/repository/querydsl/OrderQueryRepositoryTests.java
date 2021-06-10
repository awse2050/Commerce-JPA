package com.awse.commerce.repository.querydsl;

import com.awse.commerce.domains.order.entity.Order;
import com.awse.commerce.domains.order.repository.OrderQueryRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Disabled;
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
@Disabled
public class OrderQueryRepositoryTests {

    @Autowired
    private OrderQueryRepository orderQueryRepository;

    @Transactional
    @Test
    public void getMyOrderListTest() {
        Page<Order> pageList =
                orderQueryRepository.getMyOrders(2L, PageRequest.of(0,10, Sort.by("orderId").descending()));

        log.info(pageList);
        pageList.stream().forEach(page -> {
            page.getOrderItemList().stream().forEach(i -> {
                log.info(i.getOrderItemId());
            });
        });
    }

    @Transactional
    @Test
    public void getMyOrderDetailsTest() {
        Optional<Order> list = orderQueryRepository.getMyOrderDetails(7L);

        if(list.isPresent()) {
            log.info(list.get());
            log.info(list.get().getOrderItemList().get(0).getOrderItemId());
            log.info(list.get().getOrderItemList().get(1).getOrderItemId());
            log.info(list.get().getOrderer().getEmail());
        }
    }

}
