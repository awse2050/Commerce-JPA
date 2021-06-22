package com.awse.commerce.domains.cart.presentation;

import com.awse.commerce.domains.cart.service.CartService;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.util.config.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
public class CartController {

    private final CartService cartService;

    //  사용자의 장바구니 조회
    @GetMapping("/mycart")
    public String myCart(@CurrentUser Member currentMember,
                         Model model) {

        Long memberId = currentMember.getId();

        model.addAttribute("cartList", cartService.getListInCart(memberId));

        return "cart/cartDetails";
    }
}
