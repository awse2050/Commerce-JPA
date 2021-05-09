package com.awse.commerce.repository;

import com.awse.commerce.domains.delivery.entity.Delivery;
import com.awse.commerce.domains.delivery.repository.DeliveryRepository;
import com.awse.commerce.domains.item.entity.Item;
import com.awse.commerce.domains.item.repository.ItemRepository;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.repository.MemberRepository;
import com.awse.commerce.domains.order.entity.Order;
import com.awse.commerce.domains.order.entity.OrderItem;
import com.awse.commerce.domains.order.repository.OrderItemRepository;
import com.awse.commerce.domains.order.repository.OrderRepository;
import com.awse.commerce.domains.util.enums.DeliveryStatus;
import com.awse.commerce.domains.util.enums.OrderStatus;
import lombok.extern.log4j.Log4j2;
import org.aspectj.weaver.ast.Or;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class OrderRepositoryTests {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

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

        orderRepository.save(order);

        Assertions.assertThat(orderRepository.count()).isEqualTo(1);
    }

    @DisplayName("주문상태변경")
    @Test
    public void changeOrderStatusTest() {
        Order order = orderRepository.findById(1L).get();

        order.changeOrderStatus(OrderStatus.ORDERED);

        orderRepository.save(order);

        Assertions.assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDERED);
    }

    // 모든 주문 조회
    @Transactional
    @DisplayName("모든 주문 조회")
    @Test
    public void findAllOrderTest() {
        List<Order> list = orderRepository.getOrders();

        list.stream().forEach( order -> {
            log.info(order);
            log.info(order.getDeliveryInfo());
            log.info(order.getOrderer());
            log.info(order.getOrderItemList());
        });
    }

    // 모든 주문 조회
    @Transactional
    @DisplayName("모든 주문 조회")
    @Test
    public void findOrderByNameTest() {
        List<Order> list = orderRepository.findAllByName("일반회원2");

        list.stream().forEach( order -> {
            log.info(order);
            log.info(order.getOrderItemList());
        });
    }

    @Transactional
    @Commit
    @DisplayName("주문 테스트")
    @Test
    public void orderTest() {
        // 주문자
        Member member = memberRepository.findById(2L).get();

        // 배송지를 지정한다
        Delivery delivery = Delivery.builder()
                .address(member.getAddress()) // 사용자의 정보에서 주소를 추가
                .deliveryStatus(DeliveryStatus.READY)
                .build();

        //상품 지정
        Item item1 = itemRepository.getOne(2L);
        Item item2 = itemRepository.getOne(3L);

        // 주문상품 계산-> 우선 수동
        // 추후 재고수량도 계산시킴.
        OrderItem orderItem1 = OrderItem.builder().orderItemAmount(2000).orderCount(1).item(item1).build();
        OrderItem orderItem2 = OrderItem.builder().orderItemAmount(500).orderCount(1).item(item2).build();

        //  주문 상품 생성
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem1);
        orderItemList.add(orderItem2);

        Order order = Order.builder()
                .orderer(member)
                .orderStatus(OrderStatus.ORDERED)
                .deliveryInfo(delivery)
                .totalAmount(2500)
                .orderItemList(orderItemList)
                .build();

        orderRepository.save(order);
    }

    @Test
    public void findByIdTest() {
        Optional<Order> result = orderRepository.findById(5L);

        Assertions.assertThat(result).isEmpty();
    }
}
