package com.awse.commerce.domains.order.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class OrderController {

    // 체크아웃
    @GetMapping("/checkout")
    public String orderFormPage() {
        // 화면에서 주문하려고 선택한 데이터를 받아온다.

        // 해당 데이터를 변환시켜서 모델로 전송시킨다.


        return "order/order_form";
    }
}
