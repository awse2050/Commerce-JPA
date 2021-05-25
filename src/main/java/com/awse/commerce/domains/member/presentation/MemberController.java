package com.awse.commerce.domains.member.presentation;

import com.awse.commerce.domains.cart.service.CartService;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.order.service.MyOrderService;
import com.awse.commerce.domains.util.config.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    private final CartService cartService;
    private final MyOrderService myOrderService;

    // 로그인 페이지
    @GetMapping("/login")
    public String login() {
        log.info("get Login Page");
        return "login";
    }

    // 회원가입 페이지
    @GetMapping({"/signup", "/signUp"})
    public String signup() {
        log.info("get Signup Page");

        return "signup";
    }

    // 마이페이지
    // 주문조회내역, 장바구니, 찜리스트 모든 데이터를 전달한다.
    @GetMapping("/mypage")
    public String myPage(@CurrentUser Member currentMember,
                         @PageableDefault(value = 5, size = 5, sort = "orderId",direction = Sort.Direction.DESC) Pageable pageable,
                         Model model) {
        log.info("get My Page");
        // 로그인한 사용자면 해당 아이디값으로 주문내역, 장바구니, 찜목록 등을 보내준다.
        if(currentMember != null) {
            Long memberId = currentMember.getId();

            model.addAttribute("cart", cartService.getListInCart(memberId));
            model.addAttribute("orderList", myOrderService.getMyOrderList(memberId, pageable));
        } else {
            model.addAttribute("cart", null);
            model.addAttribute("orderList", null);
        }

        return "mypage";
    }

}
