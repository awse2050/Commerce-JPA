package com.awse.commerce.domains.member.presentation;

import com.awse.commerce.domains.cart.service.CartService;
import com.awse.commerce.domains.like.service.LikeService;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.order.service.MyOrderService;
import com.awse.commerce.domains.util.config.CurrentUser;
import com.awse.commerce.domains.util.pagination.PageRequestDto;
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
    private final LikeService likeService;

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
                         PageRequestDto requestDto,
                         Model model) {
        log.info("get My Page");
        // 로그인한 사용자면 해당 아이디값으로 주문내역, 장바구니, 찜목록 등을 보내준다.
        if(currentMember != null) {
            Long memberId = currentMember.getId();

            model.addAttribute("cart", cartService.getListInCart(memberId));
            // Pageable을 받고 있는 상태
            model.addAttribute("orderList", myOrderService.getMyOrderList(memberId, requestDto.getPageable("orderId")));
            model.addAttribute("like", likeService.getMyLikeList(memberId, requestDto));
        } else {
            model.addAttribute("cart", null);
            model.addAttribute("orderList", null);
            model.addAttribute("like", null);
        }

        return "mypage";
    }

}
