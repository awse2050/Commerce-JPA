package com.awse.commerce.repository;

import com.awse.commerce.domains.delivery.entity.Delivery;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.repository.MemberRepository;
import com.awse.commerce.domains.order.entity.Order;
import com.awse.commerce.domains.order.entity.OrderItem;
import com.awse.commerce.domains.order.repository.OrderItemRepository;
import com.awse.commerce.domains.order.repository.OrderRepository;
import com.awse.commerce.domains.util.enums.DeliveryStatus;
import com.awse.commerce.domains.util.enums.OrderStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class OrderRepositoryTests {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("주문 테스트")
    @Test
    public void insertOrderTest() {

        List<OrderItem> orderItemList = new ArrayList<>();

        orderItemList.add(orderItemRepository.findById(1L).get());

        Member member = memberRepository.findById(1L).get();

        Delivery delivery = Delivery.builder()
                .deliveryStatus(DeliveryStatus.READY)
                .address(member.getAddress())
                .build();

        Order order = Order.builder()
                .orderStatus(OrderStatus.ORDERED)
                .orderer(member)
                .totalAmount(2000)
                .orderItemList(orderItemList)
                .deliveryInfo(delivery)
                .build();

//        orderRepository.save(order);

        Assertions.assertThat(orderRepository.count()).isEqualTo(1);
    }
}
