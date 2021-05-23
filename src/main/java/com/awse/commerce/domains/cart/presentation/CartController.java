package com.awse.commerce.domains.cart.presentation;

import com.awse.commerce.domains.cart.service.CartService;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.util.config.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
@Log4j2
public class CartController {

    private final CartService cartService;

    // 로그인한 사용자의 장바구니 조회
    @GetMapping("/cart/user/{memberId}")
    public String findCart(@PathVariable("memberId") Long memberId,
                           @CurrentUser Member currentMember,
                           Model model) {
        log.info("MemberId in Cart : "+ memberId);
        log.warn(currentMember);
        if(currentMember != null) {
            model.addAttribute("cartList", cartService.getListInCart(memberId));
        } else {
            model.addAttribute("cartList", null);
        }

        return "cart/cartDetails";
    }

}
