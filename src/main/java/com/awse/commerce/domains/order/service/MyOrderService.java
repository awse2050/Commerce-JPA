package com.awse.commerce.domains.order.service;

import com.awse.commerce.domains.order.dto.MyOrderSummaryDto;
import com.awse.commerce.domains.order.dto.MyOrderInfoDto;
import com.awse.commerce.domains.order.dto.OrderItemDetailsDto;
import com.awse.commerce.domains.order.entity.Order;
import com.awse.commerce.domains.order.entity.OrderItem;
import com.awse.commerce.domains.order.repository.OrderQueryRepository;
import com.awse.commerce.domains.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@Transactional(readOnly = true) // 조회성능 높이기
@RequiredArgsConstructor
public class MyOrderService {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    // 나의 주문목록
    // 페이징 처리는 우선 뒷전
    public MyOrderSummaryDto getMyOrderList(Long memberId, Pageable pageable) {
        // 사용자를 검색한다.
        Page<Order> orderList = orderQueryRepository.getMyOrders(memberId,pageable);
//                orderRepository.findAllByName("일반회원2");

        // 검색해서 나온 정보를 가지고 변환시킨다.
        List<MyOrderInfoDto> myOrderInfoDtoList = orderList.stream()
                .map(order -> {
                        return  MyOrderInfoDto.builder()
                                    .orderId(order.getOrderId())
                                    .totalAmount(order.getTotalAmount())
                                    .orderStatus(order.getOrderStatus().name())
                                    .deliveryStatus(order.getDeliveryInfo().getDeliveryStatus().name())
                                    .regdate(order.getRegDate())
                                    .orderItemList(bindToDto(order.getOrderItemList())).build();
                        }).collect(Collectors.toList());

        int total = myOrderInfoDtoList.size();

        // 변환시킨 정보를 전달한다.
        return new MyOrderSummaryDto(myOrderInfoDtoList, total);
    }

    // 나의 주문의 주문상품목록

    // 변환
    public List<OrderItemDetailsDto> bindToDto(List<OrderItem> list) {
        return list.stream().map(i ->
                new OrderItemDetailsDto(
                        i.getOrderItemId(),
                        i.getOrderCount(),
                        i.getOrderItemAmount(),
                        i.getItem().getName(),
                        i.getItem().getImgPath())).collect(Collectors.toList());
    }
}
