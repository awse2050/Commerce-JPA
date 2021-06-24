package com.awse.commerce.domains.order.service;

import com.awse.commerce.domains.order.dto.*;
import com.awse.commerce.domains.order.entity.Order;
import com.awse.commerce.domains.order.exception.OrderBadRequestException;
import com.awse.commerce.domains.order.repository.OrderQueryRepository;
import com.awse.commerce.domains.util.pagination.PageRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@Transactional(readOnly = true) // 조회성능 높이기
@RequiredArgsConstructor
public class MyOrderService {

    private final OrderQueryRepository orderQueryRepository;

    // 나의 주문목록
    // 마이페이지에서 보여줄 목록
    public MyOrderSummaryDto getMyOrderList(Long memberId, Pageable pageable) {
        // 사용자를 검색한다.
        Page<Order> orderList = orderQueryRepository.getMyOrders(memberId, pageable ,null);

        List<MyOrderDto> myOrderDtoList = MyOrderDto.from(orderList);

        int total = myOrderDtoList.size();

        // 변환시킨 정보를 전달한다.
        return new MyOrderSummaryDto(myOrderDtoList, total);
    }

    // 나의 주문목록 + 페이징
    // 주문내역조회 페이지에서 보여줄 목록
    public PageResultOrderDto<MyOrderDto, Order> getMyOrderWithPaging(Long memberId, PageRequestDto requestDto, String keyword) {
        // 사용자를 검색한다.
        Page<Order> orderList = orderQueryRepository.getMyOrders(memberId, requestDto.getPageable("orderId"), keyword);

        List<MyOrderDto> myOrderDtoList = MyOrderDto.from(orderList);

        // 변환시킨 정보를 전달한다.
        return new PageResultOrderDto<>(myOrderDtoList, orderList);
    }

    // 나의 특정 주문의 정보. ( 현재 미사용 )
    public MyOrderDetailsDto getMyOrderDetails(Long orderId) {
        // 주문찾기
        Order entity = orderQueryRepository.getMyOrderDetails(orderId)
                .orElseThrow(() -> new OrderBadRequestException("존재하지 않는 주문번호입니다."));
        // 주문상품에 존재하는 상품목록 만들기
        List<MyOrderDetailsItemDto> myOrderDetailsItemDtoList =
                MyOrderDetailsItemDto.from(entity.getOrderItemList());

        // 변환할 주문에대한 정보 전달하기.
        // 주문번호, 주문상태, 주문날짜, 주문 상품의 목록들
        MyOrderDetailsDto myOrderDetailsDto = MyOrderDetailsDto.builder()
                .orderId(entity.getOrderId())
                .orderStatus(entity.getOrderStatus().name())
                .orderedItemList(myOrderDetailsItemDtoList)
                .regdate(entity.getRegDate())
                .build();

        return myOrderDetailsDto;
    }
}
