package com.awse.commerce.service;

import com.awse.commerce.domains.order.dto.MyOrderSummaryDto;
import com.awse.commerce.domains.order.service.MyOrderService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SpringBootTest
@Log4j2
public class MyOrderServiceTests {

    @Autowired
    private MyOrderService myOrderService;

    @Test
    public void getMyOrderListTest() {
        MyOrderSummaryDto summaryDto = myOrderService.getMyOrderList(2L, PageRequest.of(0,10, Sort.by("orderId").descending()));

        summaryDto.getMyOrderDtoList().stream().forEach(i -> log.info( i.getOrderId()));
    }


}
