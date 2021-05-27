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

        return "order/order_form";
    }
}
