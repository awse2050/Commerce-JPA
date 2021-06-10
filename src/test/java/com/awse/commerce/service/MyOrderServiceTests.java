package com.awse.commerce.service;

import com.awse.commerce.domains.order.dto.MyOrderDetailsDto;
import com.awse.commerce.domains.order.dto.MyOrderDto;
import com.awse.commerce.domains.order.dto.MyOrderSummaryDto;
import com.awse.commerce.domains.order.dto.PageResultOrderDto;
import com.awse.commerce.domains.order.entity.Order;
import com.awse.commerce.domains.order.service.MyOrderService;
import com.awse.commerce.domains.util.pagination.PageRequestDto;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SpringBootTest
@Log4j2
@Disabled
public class MyOrderServiceTests {

    @Autowired
    private MyOrderService myOrderService;

    @DisplayName("내가 주문한 전체목록 조회")
    @Test
    public void getMyOrderListTest() {
        MyOrderSummaryDto summaryDto =
                myOrderService.getMyOrderList(1L, PageRequest.of(0,10, Sort.by("orderId").descending()));

        summaryDto.getMyOrderDtoList().stream().forEach(i -> log.info( i.getOrderId()));
    }

    @DisplayName("특정 주문의 정보 조회")
    @Test
    public void getMyOrderDetailsTest() {
        MyOrderDetailsDto myOrderDetailsDto = myOrderService.getMyOrderDetails(7L);

        Assertions.assertThat(myOrderDetailsDto.getOrderId()).isEqualTo(7L);
    }

    @DisplayName("나의 주문 조회 테스트 + 페이징")
    @Test
    public void myOrderFindandPaging() {
        PageRequestDto pageRequestDto = new PageRequestDto();

        PageResultOrderDto<MyOrderDto, Order> list = myOrderService.getMyOrderWithPaging(1L, pageRequestDto);

        Assertions.assertThat(list).isNotNull();
        list.getDtoList().forEach(i -> {
            log.info(i.getOrderId());
            log.info(i.getRepresentativeImgPath());
            log.info(i.getRepresentativeItemName());
        });
    }
}
