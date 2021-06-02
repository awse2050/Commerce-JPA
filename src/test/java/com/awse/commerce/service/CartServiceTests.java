package com.awse.commerce.service;

import com.awse.commerce.domains.cart.dao.AddRequestItemDao;
import com.awse.commerce.domains.cart.dao.CheckoutDao;
import com.awse.commerce.domains.cart.dao.ModifyRequestItemDao;
import com.awse.commerce.domains.cart.dao.RemoveItemDao;
import com.awse.commerce.domains.cart.dto.CartListDto;
import com.awse.commerce.domains.cart.dto.CheckoutDaoListDto;
import com.awse.commerce.domains.cart.dto.CheckoutItemListDto;
import com.awse.commerce.domains.cart.service.CartService;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        cartService.addToCart(1L, new AddRequestItemDao(8L, 1));

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

    @DisplayName("선택삭제 테스트")
    @Test
    @Transactional
    @Commit
    public void selectRemoveTest() {
        RemoveItemDao dao = new RemoveItemDao();
        dao.getItemIdList().add(6L);
        dao.getItemIdList().add(8L);

        cartService.selectRemoveItemsInCart(1L, dao);

        Assertions.assertThat(dao.getItemIdList().size()).isEqualTo(2);
        Assertions.assertThat(cartService.getListInCart(1L).getCartItemDetailsDtoList().size()).isEqualTo(4);
    }

    @DisplayName("전체 삭제 테스트")
    @Test
    @Transactional
    @Commit
    public void removeAllItemTest() {

        Long memberId = 1L;

        cartService.removeItemsInCart(1L);
        Assertions.assertThat(cartService.getListInCart(1L).getCartItemDetailsDtoList().size()).isEqualTo(0);

    }

    @DisplayName("장바구니 체크아웃 상품 목록 확인 테스트")
    @Test
    public void checkoutItemListConfirmTest() {

        Long memberId = 1L;

        List<CheckoutDao> daoList = new ArrayList<>();

        daoList.add(new CheckoutDao(121L, 2));
        daoList.add(new CheckoutDao(8L, 1));

        CheckoutDaoListDto dtoList1 = new CheckoutDaoListDto(daoList);

        CheckoutItemListDto dtoList = cartService.getCheckoutItems(memberId, dtoList1);

        dtoList.getCheckoutList().forEach(i -> log.info(i));

        Assertions.assertThat(dtoList.getCheckoutList().size()).isEqualTo(2);

    }
}

