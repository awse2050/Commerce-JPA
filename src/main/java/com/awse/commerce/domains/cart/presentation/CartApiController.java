package com.awse.commerce.domains.cart.presentation;

import com.awse.commerce.domains.cart.dao.ModifyRequestItemDao;
import com.awse.commerce.domains.cart.service.CartService;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.util.config.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Log4j2
public class CartApiController {

    private final CartService cartService;

    private static final String API_URI = "/api/cart";
    // 장바구니 상품 수정하기
    @PutMapping(API_URI+"/{itemId}")
    public ResponseEntity<Long> modifyitemCountInCart(@PathVariable("itemId")Long itemId,
                                      @RequestBody ModifyRequestItemDao dao, // 추후 @Valid 적용시키기
                                      @CurrentUser Member currentMember) {
        log.info(itemId);
        log.info("변경 요청한 상품번호 : "+dao.getItemId());
        log.info("변경 요청한 삼품 개수 : " +dao.getOrderCount());
        cartService.modifyItemInCart(currentMember.getId(), dao);

        return new ResponseEntity<>(currentMember.getId(), HttpStatus.OK);
    }
}
