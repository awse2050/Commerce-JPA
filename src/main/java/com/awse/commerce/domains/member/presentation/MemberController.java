package com.awse.commerce.domains.member.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Log4j2
public class MemberController {

    // 로그인 페이지
    @GetMapping("/login")
    public String login() {
        log.info("get Login Page");
        return "login";
    }

    // 마이페이지
    // 주문조회내역, 장바구니, 찜리스트 모든 데이터를 전달한다.
    @GetMapping("/mypage")
    public String myPage() {
        log.info("get My Page");
        return "mypage";
    }

}
