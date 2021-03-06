package com.awse.commerce.domains.order.service;

import com.awse.commerce.domains.cart.service.CartService;
import com.awse.commerce.domains.delivery.entity.Delivery;
import com.awse.commerce.domains.item.entity.Item;
import com.awse.commerce.domains.item.exception.ItemBadRequestException;
import com.awse.commerce.domains.item.repository.ItemRepository;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.exception.MemberBadRequestException;
import com.awse.commerce.domains.member.repository.MemberRepository;
import com.awse.commerce.domains.order.dao.OrderRequestDao;
import com.awse.commerce.domains.order.dto.OrderRequestDto;
import com.awse.commerce.domains.order.entity.Order;
import com.awse.commerce.domains.order.entity.OrderItem;
import com.awse.commerce.domains.order.exception.OrderBadRequestException;
import com.awse.commerce.domains.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    private final CartService cartService;

    @Transactional
    // 주문 ( 주문자번호, 주문상품 데이터 )
    public Long order(Long ordererId, OrderRequestDao orderRequestDao) {
        // 주문자를 찾는다.
        Member orderer = memberRepository.findById(ordererId).
                orElseThrow(() -> new MemberBadRequestException("존재하지 않는 사용자입니다."));
        // 배송지를 지정한다.
        Delivery delivery = new Delivery(orderer.getAddress());
        // 주문상품을 모은다.
        List<OrderRequestDto> orderRequestDtoList = orderRequestDao.getOrderRequestDtoList();

        List<OrderItem> orderItemList = orderRequestDtoList.stream().map(item -> {
            Item itemEntity = itemRepository.findById(item.getItemId()).
                    orElseThrow(() -> new ItemBadRequestException());

            return new OrderItem(itemEntity, item.getOrderCount());

        }).collect(Collectors.toList());

        // 상품 재고를 계산한다.
        orderItemList.stream()
                .forEach(orderItem -> orderItem.removeStockQuantity());

        // save
        Order order = new Order(orderer, delivery, orderItemList);

        Long orderResult = orderRepository.save(order).getOrderId();
        // 장바구니 비우기
        orderRequestDtoList.forEach(item -> {
            cartService.removeItemInCart(ordererId, item.getItemId());
        });

        return orderResult;
    }

    // 주문취소하기
    public void orderCancel(Long orderId) {
        // 주문찾기
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderBadRequestException("없는 주문 입니다."));
        // 주문 취소기능 사용
        order.cancel();
    }
}
