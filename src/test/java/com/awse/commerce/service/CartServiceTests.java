package com.awse.commerce.service;

import com.awse.commerce.domains.cart.dao.AddRequestItemDao;
import com.awse.commerce.domains.cart.dao.CheckoutDao;
import com.awse.commerce.domains.cart.dao.ModifyRequestItemDao;
import com.awse.commerce.domains.cart.dao.RemoveItemDao;
import com.awse.commerce.domains.cart.dto.CartListDto;
import com.awse.commerce.domains.cart.dto.CheckoutDaoListDto;
import com.awse.commerce.domains.cart.dto.CheckoutItemListDto;
import com.awse.commerce.domains.cart.exception.CartNotFoundException;
import com.awse.commerce.domains.cart.repository.CartRepository;
import com.awse.commerce.domains.cart.service.CartService;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Log4j2
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Transactional
public class CartServiceTests {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    private final Long memberId = 1L;

    @DisplayName("장바구니 생성 테스트")
    @Test
    @Commit
    @Disabled
    public void createCartTest() {
        Long result = cartService.createCart(memberId);

//        Assertions.assertThat(result).isEqualTo(1L);
    }

    @DisplayName("장바구니에 상품 담기 테스트")
    @Test
    @Commit
    @Order(1)
    public void addToCartTest1() {
        cartService.addToCart(memberId, new AddRequestItemDao(102L, 1));
        cartService.addToCart(memberId, new AddRequestItemDao(103L, 1));
        cartService.addToCart(memberId, new AddRequestItemDao(104L, 1));

        Assertions.assertThat(cartRepository.findByMemberId(memberId).
                orElseThrow(() -> new CartNotFoundException()).getCartMap().size()).isEqualTo(3);

    }

    @DisplayName("장바구니에 있는 상품 중복으로 담기")
    @Test
    @Commit
    @Order(2)
    public void addToCartTest2() {
        cartService.addToCart(memberId, new AddRequestItemDao(103L, 1));

        Assertions.assertThat(cartRepository.findByMemberId(memberId).
                orElseThrow(() -> new CartNotFoundException()).getCartMap().get(103L).getOrderCount()).isEqualTo(2);

    }

    @DisplayName("장바구니에 있는 상품 수정하기")
    @Test
    @Commit
    @Order(3)
    public void modifyItemInCartTest() {


        cartService.modifyItemInCart(memberId, new ModifyRequestItemDao(104L, 5));

        Assertions.assertThat(cartRepository.findByMemberId(memberId).
                orElseThrow(() -> new CartNotFoundException()).getCartMap().get(104L).getOrderCount()).isEqualTo(5);

    }

    @DisplayName("장바구니 목록 변환 테스트")
    @Test
    @Order(4)
    public void getCartList() {

        CartListDto dto = cartService.getListInCart(memberId);

        Assertions.assertThat(dto.getCartItemDetailsDtoList().get(0)).isNotNull();
    }

    @DisplayName("선택삭제 테스트")
    @Test
    @Commit
    @Order(5)
    public void selectRemoveTest() {
        RemoveItemDao dao = new RemoveItemDao();
        dao.getItemIdList().add(102L);

        cartService.selectRemoveItemsInCart(memberId, dao);

        Assertions.assertThat(dao.getItemIdList().size()).isEqualTo(1);
        Assertions.assertThat(cartService.getListInCart(memberId).getCartItemDetailsDtoList().size()).isEqualTo(2);
    }

    @DisplayName("전체 삭제 테스트")
    @Test
    @Commit
    @Order(6)
    public void removeAllItemTest() {

        cartService.removeItemsInCart(memberId);
        Assertions.assertThat(cartService.getListInCart(memberId).getCartItemDetailsDtoList().size()).isEqualTo(0);

    }

    @DisplayName("장바구니 체크아웃 상품 목록 확인 테스트")
    @Test
    @Disabled
    public void checkoutItemListConfirmTest() {

        List<CheckoutDao> daoList = new ArrayList<>();

        daoList.add(new CheckoutDao(121L, 2));
        daoList.add(new CheckoutDao(8L, 1));

        CheckoutDaoListDto dtoList1 = new CheckoutDaoListDto(daoList);

        CheckoutItemListDto dtoList = cartService.getCheckoutItems(memberId, dtoList1);

        dtoList.getCheckoutList().forEach(i -> log.info(i));

        Assertions.assertThat(dtoList.getCheckoutList().size()).isEqualTo(2);

    }
}

