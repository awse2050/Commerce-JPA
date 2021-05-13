package com.awse.commerce.domains.order.service;

import com.awse.commerce.domains.order.dto.MyOrderDto;
import com.awse.commerce.domains.order.dto.MyOrderSummaryDto;
import com.awse.commerce.domains.order.entity.Order;
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

        List<MyOrderDto> myOrderDtoList = orderList.stream()
                .map(o -> MyOrderDto.builder()
                        .orderId(o.getOrderId())
                        .orderStatus(o.getOrderStatus().name())
                        .representativeImgPath(o.getOrderItemList().get(0).getItem().getImgPath())
                        .representativeItemName(o.getOrderItemList().get(0).getItem().getName())
                        .totalAmount(o.getTotalAmount())
                        .build()
                        ).collect(Collectors.toList());

        int total = myOrderDtoList.size();

        // 변환시킨 정보를 전달한다.
        return new MyOrderSummaryDto(myOrderDtoList, total);
    }

    // 나의 주문의 주문상품목록


}
