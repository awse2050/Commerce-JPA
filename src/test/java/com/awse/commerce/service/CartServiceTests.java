package com.awse.commerce.service;

import com.awse.commerce.domains.cart.dao.AddRequestItemDao;
import com.awse.commerce.domains.cart.service.CartService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class CartServiceTests {

    @Autowired
    private CartService cartService;

    @DisplayName("장바구니 생성 테스트")
    @Test
    @Transactional
    @Commit
    public void createCartTest() {
        Long result = cartService.createCart(1L);

        Assertions.assertThat(result).isEqualTo(1L);
    }
    @DisplayName("장바구니에 상품 담기 테스트")
    @Test
    @Transactional
    @Commit
    public void addToCartTest1() {
        cartService.addToCart(1L, new AddRequestItemDao(1L, 2));


    }

    @DisplayName("장바구니에 있는 상품 중복으로 담기")
    @Test
    @Transactional
    @Commit
    public void addToCartTest2() {
        cartService.addToCart(1L, new AddRequestItemDao(1L, 1));
    }
}

