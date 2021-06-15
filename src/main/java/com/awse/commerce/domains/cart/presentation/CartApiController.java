package com.awse.commerce.domains.cart.presentation;

import com.awse.commerce.domains.cart.dao.AddRequestItemDao;
import com.awse.commerce.domains.cart.dao.ModifyRequestItemDao;
import com.awse.commerce.domains.cart.dao.RemoveItemDao;
import com.awse.commerce.domains.cart.service.CartService;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.util.config.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Log4j2
public class CartApiController {

    private final CartService cartService;

    private static final String API_URI = "/api/cart";

    // 장바구니 상품 수정하기
    @PutMapping(API_URI+"/{itemId}")
    public ResponseEntity<Long> modifyitemCountInCart(@PathVariable("itemId")Long itemId,
                                                      @RequestBody @Valid ModifyRequestItemDao dao, // 추후 @Valid 적용시키기
                                                      @CurrentUser Member currentMember) {
        log.info(itemId);
        log.info("변경 요청한 상품번호 : "+dao.getItemId());
        log.info("변경 요청한 삼품 개수 : " +dao.getOrderCount());
        cartService.modifyItemInCart(currentMember.getId(), dao);

        return new ResponseEntity<>(currentMember.getId(), HttpStatus.OK);
    }

    // 상세페이지에서 장바구니로 담기
    @PostMapping(API_URI)
    public ResponseEntity<String> addToCart(@RequestBody @Valid AddRequestItemDao dao,
                                          @CurrentUser Member currentMember) {
        if(currentMember == null) {
            log.info(currentMember);
            return new ResponseEntity<>("로그인이 필요한 서비스입니다.",HttpStatus.BAD_REQUEST);
        } else {
            Long memberId = currentMember.getId();

            cartService.addToCart(memberId, dao);

            return new ResponseEntity<>("success", HttpStatus.OK);
        }
    }

    // 장바구니에서 상품하나 삭제하기
    @DeleteMapping(API_URI+"/{itemId}")
    public ResponseEntity<String> removeItemInCart(@PathVariable("itemId") Long itemId,
                                    @CurrentUser Member currentMember) {
        log.warn("remove itemId : "+ itemId);
        if(currentMember == null) {
            log.info(currentMember);
            return new ResponseEntity<>("로그인이 필요한 서비스입니다.",HttpStatus.BAD_REQUEST);
        } else {
            Long memberId = currentMember.getId();

            cartService.removeItemInCart(memberId, itemId);

            return new ResponseEntity<>("remove", HttpStatus.OK);
        }
    }

    // 장바구니 선택 및 전체삭제
    @DeleteMapping(API_URI)
    public ResponseEntity<String> selectRemoveItemsInCart(@RequestBody RemoveItemDao dao,
                                                          @CurrentUser Member currentMember) {

        log.warn("삭제할 아이템 개수 : " + dao.getItemIdList().size());

        if(currentMember == null) {
            log.info(currentMember);
            return new ResponseEntity<>("로그인이 필요한 서비스입니다.",HttpStatus.BAD_REQUEST);
        } else {
            Long memberId = currentMember.getId();

            cartService.selectRemoveItemsInCart(memberId, dao);

            return new ResponseEntity<>("remove", HttpStatus.OK);
        }
    }

    // 장바구니 아이템 전체 삭제
    @DeleteMapping(API_URI+"/all")
    public ResponseEntity<String> allRemoveItemsInCart(@CurrentUser Member currentMember) {

        if(currentMember == null) {
            log.info(currentMember);
            return new ResponseEntity<>("로그인이 필요한 서비스입니다.",HttpStatus.BAD_REQUEST);
        } else {
            Long memberId = currentMember.getId();

            cartService.removeItemsInCart(memberId);

            return new ResponseEntity<>("remove", HttpStatus.OK);
        }
    }
}
