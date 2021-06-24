package com.awse.commerce.repository;

import com.awse.commerce.domains.cart.dao.CheckoutDao;
import com.awse.commerce.domains.cart.dto.CartItemDetailsDto;
import com.awse.commerce.domains.cart.dto.CheckoutItemListDto;
import com.awse.commerce.domains.cart.entity.Cart;
import com.awse.commerce.domains.cart.repository.CartRepository;
import com.awse.commerce.domains.item.entity.Item;
import com.awse.commerce.domains.item.repository.ItemRepository;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
@Log4j2
public class CartRepositoryTests {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @DisplayName("장바구니 조회 테스트")
    @Transactional
    @Test
    public void findByMemberIdTest() {
        Optional<Cart> result = cartRepository.findByMemberId(1L);

        log.info(result.get());
        Assertions.assertThat(result.isPresent()).isTrue();

    }

    @DisplayName("장바구니 상품 선택한 목록 데이터 변환하기")
    @Transactional
    @Test
    public void bindToCheckoutItemIdDtoTest() {
        // 사용자의 카트를 찾는다.
        Cart cart = cartRepository.findByMemberId(1L).get();
         // 상품번호와 주문개수를 받는다.
        List<CheckoutDao> list = new ArrayList<>();
        // local - 1L , EC2 - 102L
        list.add(new CheckoutDao(102L, 1));

         // 해당 객체를 돌려서 itemId로 상품을 찾고
        CheckoutItemListDto itemListDto = new CheckoutItemListDto();

        List<CartItemDetailsDto> itemDetailsDtoList = list.stream().map(dao -> {
            Item item = itemRepository.findById(dao.getItemId()).get();

            return CartItemDetailsDto.builder()
                    .itemId(dao.getItemId())
                    .itemAmount(item.getMoney())
                    .imgPath(item.getImgPath())
                    .itemName(item.getName())
                    .orderCount(dao.getOrderCount())
                    .build();
        }).collect(Collectors.toList());

        itemListDto.setCheckoutList(itemDetailsDtoList);

        itemListDto.getCheckoutList().forEach(i -> {
            log.info(i);
        });

    }
}
