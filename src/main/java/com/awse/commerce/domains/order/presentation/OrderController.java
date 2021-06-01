package com.awse.commerce.domains.order.presentation;

import com.awse.commerce.domains.cart.dto.CheckoutDaoListDto;
import com.awse.commerce.domains.cart.dto.CheckoutItemListDto;
import com.awse.commerce.domains.cart.service.CartService;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.util.config.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Log4j2
@PropertySource("classpath:application-pay.properties")
public class OrderController {

    private final CartService cartService;

    private static CheckoutDaoListDto daoList;

    @Value("${bootpay.application.id}")
    private String application_id;

    // 체크아웃
    @PostMapping("/checkout")
    @ResponseBody
    public ResponseEntity<String> orderFormPage(@RequestBody CheckoutDaoListDto daoListDto,
                                                @CurrentUser Member currentMember) {
        // 로그인이 끝났거나 아닐경우
        if(currentMember == null) {
            return new ResponseEntity<>("로그인이 필요한 서비스 입니다.", HttpStatus.BAD_REQUEST);
        }
        // 화면에서 주문하려고 선택한 데이터를 받아온다.
        log.warn(daoListDto);
        // 서버에 저장
        daoList = daoListDto;

        return new ResponseEntity<>("/order/order_form", HttpStatus.OK);
    }

    // 주문 및 결제 페이지
    @GetMapping("/order/order_form")
    public String orderFormPage(@CurrentUser Member currentMember ,Model model) {
        log.warn(daoList);
        // 체크아웃 상품목록
        CheckoutItemListDto checkoutItemList = cartService.getCheckoutItems(currentMember.getId(), daoList);

        model.addAttribute("member", currentMember);
        model.addAttribute("checkoutItemList", checkoutItemList);
        model.addAttribute("applicationId", application_id);

        return "order/order_form";
    }
}
