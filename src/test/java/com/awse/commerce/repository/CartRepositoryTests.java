package com.awse.commerce.repository;

import com.awse.commerce.domains.cart.entity.Cart;
import com.awse.commerce.domains.cart.repository.CartRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Log4j2
public class CartRepositoryTests {

    @Autowired
    private CartRepository cartRepository;

    @DisplayName("장바구니 조회 테스트")
    @Transactional
    @Test
    public void findByMemberIdTest() {
        Optional<Cart> result = cartRepository.findByMemberId(5L);

        log.info(result.get());
        log.info(result.get().getCartMap().get(1L).getItemId());
        log.info(result.get().getCartMap().get(2L).getItemId());

    }
}
