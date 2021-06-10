package com.awse.commerce.service;

import com.awse.commerce.domains.cart.dto.CartListDto;
import com.awse.commerce.domains.cart.repository.CartRepository;
import com.awse.commerce.domains.cart.service.CartService;
import com.awse.commerce.domains.item.repository.ItemRepository;
import com.awse.commerce.domains.order.dao.OrderRequestDao;
import com.awse.commerce.domains.order.dto.OrderRequestDto;
import com.awse.commerce.domains.order.repository.OrderRepository;
import com.awse.commerce.domains.order.service.OrderService;
import com.awse.commerce.domains.util.enums.OrderStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
@Disabled
public class OrderServiceTests {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartService cartService;

    @DisplayName("OrderService's order Method Tests")
    @Test
    @Commit
    public void orderTest() {
        // given
        List<OrderRequestDto> orderRequestDtoList = new ArrayList<>();

        orderRequestDtoList.add(new OrderRequestDto(120L, 1));
        orderRequestDtoList.add(new OrderRequestDto(119L, 1));

        // when
        orderService.order(1L, new OrderRequestDao(orderRequestDtoList));

        // then
       // Assertions.assertThat(itemRepository.findById(20L).get().getStockQuantity()).isEqualTo(20);

//        Optional<Order> result = orderRepository.findById(8L);
//        Order order = null;
//
//        if(result.isPresent()){
//
//            order = result.get();
//        }
//
//        Assertions.assertThat(order).isNotNull();
    }

    @DisplayName("주문 취소 테스트")
    @Test
    @Commit
    public void orderCancelTest1() {
        orderService.orderCancel(1L);

        Assertions.assertThat(orderRepository.findById(1L).get().getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
    }

    @DisplayName("주문 취소시 재고 수량 테스트")
    @Test
    @Commit
    public void orderCancelTest2() {
        orderService.orderCancel(1L);

        Assertions.assertThat(orderRepository.findById(1L).get().getOrderItemList().get(0).getItem().getStockQuantity()).isEqualTo(2);
    }

    @DisplayName("주문 후 장바구니 목록 비우기 테스트")
    @Test
    @Commit
    public void orderAndCartRemoveTest() {

        CartListDto dto = cartService.getListInCart(1L);
        List<OrderRequestDto> dtoList = dto.getCartItemDetailsDtoList().stream().map(
                item -> {
                    return OrderRequestDto.builder()
                            .itemId(item.getItemId())
                            .orderCount(item.getOrderCount())
                            .build();
                }).collect(Collectors.toList());

        orderService.order(1L, new OrderRequestDao(dtoList));

        CartListDto testDto = cartService.getListInCart(1L);

        Assertions.assertThat(orderRepository.findAll().size()).isGreaterThan(0);
        Assertions.assertThat(testDto.getCartTotal()).isEqualTo(0);

    }

}
