package com.awse.commerce.service;

import com.awse.commerce.domains.cart.dao.AddRequestItemDao;
import com.awse.commerce.domains.cart.dao.ModifyRequestItemDao;
import com.awse.commerce.domains.cart.dto.CartListDto;
import com.awse.commerce.domains.cart.service.CartService;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Log4j2
public class CartServiceTests {

    @Autowired
    private CartService cartService;

    @DisplayName("장바구니 생성 테스트")
    @Test
    @Transactional
    @Commit
    public void createCartTest() {
        Long result = cartService.createCart(1L);

//        Assertions.assertThat(result).isEqualTo(1L);
    }
    @DisplayName("장바구니에 상품 담기 테스트")
    @Test
    @Transactional
    @Commit
    public void addToCartTest1() {
        cartService.addToCart(1L, new AddRequestItemDao(6L, 1));

    }

    @DisplayName("장바구니에 있는 상품 중복으로 담기")
    @Test
    @Transactional
    @Commit
    public void addToCartTest2() {
        cartService.addToCart(1L, new AddRequestItemDao(1L, 1));
    }

    @DisplayName("장바구니에 있는 상품 수정하기")
    @Test
    @Transactional
    @Commit
    public void modifyItemInCartTest() {
        cartService.modifyItemInCart(1L, new ModifyRequestItemDao(1L, 5));
    }

    @DisplayName("장바구니 목록 변환")
    @Test
    @Transactional
    public void getCartList() {

       CartListDto dto = cartService.getListInCart(5L);

       Assertions.assertThat(dto.getCartTotal()).isEqualTo(3);
       Assertions.assertThat(dto.getCartItemDetailsDtoList().get(0)).isNotNull();
    }

}

