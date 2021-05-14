package com.awse.commerce.domains.order.service;

import com.awse.commerce.domains.item.entity.Item;
import com.awse.commerce.domains.order.dto.MyOrderDetailsDto;
import com.awse.commerce.domains.order.dto.MyOrderDetailsItemDto;
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
import java.util.Optional;
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

    // 나의 특정 주문의 정보.
    public MyOrderDetailsDto getMyOrderDetails(Long orderId) {
        // 주문찾기
        Order entity = orderQueryRepository.getMyOrderDetails(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문번호입니다."));
        // 주문상품에 존재하는 상품목록 만들기
        List<MyOrderDetailsItemDto>  myOrderDetailsItemDtoList =
                entity.getOrderItemList().stream()
                        .map(itemList -> {
                            Item item = itemList.getItem();
                            return new MyOrderDetailsItemDto(item.getItemId(), item.getName(), item.getMoney(), item.getImgPath());
                        }).collect(Collectors.toList());
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
