package com.awse.commerce.domains.order.service;

import com.awse.commerce.domains.delivery.entity.Delivery;
import com.awse.commerce.domains.item.entity.Item;
import com.awse.commerce.domains.item.repository.ItemRepository;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.repository.MemberRepository;
import com.awse.commerce.domains.order.dao.OrderRequestDao;
import com.awse.commerce.domains.order.entity.Order;
import com.awse.commerce.domains.order.entity.OrderItem;
import com.awse.commerce.domains.order.repository.OrderRepository;
import com.awse.commerce.domains.util.enums.DeliveryStatus;
import com.awse.commerce.domains.util.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    // 주문 ( 주문자번호, 주문상품 데이터 )
    public Long order(Long ordererId, OrderRequestDao orderRequestDao) {
        // 주문자를 찾는다.
        Member orderer = memberRepository.findById(ordererId).get();
        // 배송지를 지정한다.
        Delivery delivery = Delivery.builder()
                .deliveryStatus(DeliveryStatus.READY)
                .address(orderer.getAddress())
                .build();
        // 주문상품을 모은다.
        List<OrderItem> orderItemList = orderRequestDao.getOrderRequestDtoList().stream().map(item -> {
            Item itemEntity = itemRepository.findById(item.getItemId()).get();

            return OrderItem.builder()
                    .item(itemEntity)
                    .orderItemAmount(itemEntity.getMoney() * item.getOrderCount())
                    .orderCount(item.getOrderCount())
                    .build();

        }).collect(Collectors.toList());
        // save
        Order order = Order.builder()
                .orderer(orderer)
                .orderStatus(OrderStatus.ORDERED)
                .deliveryInfo(delivery)
                .totalAmount(orderItemList.stream().mapToInt(orderitem -> orderitem.getOrderItemAmount()).sum())
                .orderItemList(orderItemList)
                .build();

        Long orderResult = orderRepository.save(order).getOrderId();

        return orderResult;
    }
}
