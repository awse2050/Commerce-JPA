package com.awse.commerce.service;

import com.awse.commerce.domains.delivery.entity.Delivery;
import com.awse.commerce.domains.item.entity.Item;
import com.awse.commerce.domains.item.repository.ItemRepository;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.repository.MemberRepository;
import com.awse.commerce.domains.order.dao.OrderRequestDao;
import com.awse.commerce.domains.order.dto.OrderRequestDto;
import com.awse.commerce.domains.order.entity.Order;
import com.awse.commerce.domains.order.entity.OrderItem;
import com.awse.commerce.domains.order.repository.OrderItemRepository;
import com.awse.commerce.domains.order.repository.OrderRepository;
import com.awse.commerce.domains.order.service.OrderService;
import com.awse.commerce.domains.util.enums.DeliveryStatus;
import com.awse.commerce.domains.util.enums.OrderStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class OrderServiceTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderService orderService;

    // TDD 형식으로 메서드이름을 똑같이 사용.
    @DisplayName("주문하기")
    @Test
    @Transactional
    @Commit
    public void order() {
        // 2개의 파라미터 (사용자번호, 주문상품목록)
        Member member = memberRepository.findById(4L).get();

        Delivery delivery = Delivery.builder().deliveryStatus(DeliveryStatus.READY).address(member.getAddress()).build();

        //실제로는 파라미터로 받은 '주문상품목록'에 존재하는 데이터를 뺴냄.
        Item item1 = itemRepository.findById(6L).get();
        Item item2 = itemRepository.findById(7L).get();

        OrderItem orderItem1 = OrderItem.builder().orderCount(1).orderItemAmount(item1.getMoney()).item(item1).build();
        OrderItem orderItem2 = OrderItem.builder().orderCount(1).orderItemAmount(item2.getMoney()).item(item2).build();

        List<OrderItem> orderItemList = new ArrayList<>();

        orderItemList.add(orderItem1);
        orderItemList.add(orderItem2);

        Order order = Order.builder()
                .orderStatus(OrderStatus.ORDERED)
                .deliveryInfo(delivery)
                .totalAmount(orderItemList.stream().mapToInt(item-> item.getOrderItemAmount()).sum())
                .orderer(member)
                .orderItemList(orderItemList)
                .build();

        orderRepository.save(order);

        Assertions.assertThat(orderRepository.count()).isEqualTo(4);
    }

    @DisplayName("OrderService's order Method Tests")
    @Test
    @Transactional
    @Commit
    public void orderTest() {
        // given
        List<OrderRequestDto> orderRequestDtoList = new ArrayList<>();

        orderRequestDtoList.add(new OrderRequestDto(3L, 1));
        orderRequestDtoList.add(new OrderRequestDto(2L, 2));

        // when
        orderService.order(6L, new OrderRequestDao(orderRequestDtoList));

        // then
        Optional<Order> result = orderRepository.findById(7L);
        Order order = null;

        if(result.isPresent()){

            order = result.get();
        }

        Assertions.assertThat(order).isNotNull();

    }

}
