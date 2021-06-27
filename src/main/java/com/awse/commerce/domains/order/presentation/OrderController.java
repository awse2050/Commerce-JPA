package com.awse.commerce.domains.order.presentation;

import com.awse.commerce.domains.cart.dto.CheckoutDaoListDto;
import com.awse.commerce.domains.cart.dto.CheckoutItemListDto;
import com.awse.commerce.domains.cart.service.CartService;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.order.service.MyOrderService;
import com.awse.commerce.domains.util.config.security.CurrentUser;
import com.awse.commerce.domains.util.pagination.PageRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Log4j2
@PropertySource("classpath:application-pay.properties")
public class OrderController {

    private static CheckoutDaoListDto daoList;
    private final CartService cartService;
    private final MyOrderService myOrderService;
    @Value("${bootpay.application.id}")
    private String application_id;

    // 체크아웃
    @PostMapping("/checkout")
    @ResponseBody
    public ResponseEntity<String> orderFormPage(@RequestBody CheckoutDaoListDto daoListDto,
                                                @CurrentUser Member currentMember) {
        // 로그인이 끝났거나 아닐경우
        if (currentMember == null) {
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
    public String orderFormPage(@CurrentUser Member currentMember, Model model) {
        log.warn(daoList);
        // 체크아웃 상품목록
        CheckoutItemListDto checkoutItemList = cartService.getCheckoutItems(currentMember.getId(), daoList);

        model.addAttribute("member", currentMember);
        model.addAttribute("checkoutItemList", checkoutItemList);
        model.addAttribute("applicationId", application_id);

        return "order/order_form";
    }

    // 전체 주문내역 조회 페이지
    @GetMapping("/order/order_list")
    public String orderListPage(@CurrentUser Member currentMember,
                                PageRequestDto requestDto,
                                String keyword,
                                Model model) {

        Long memberId = currentMember.getId();

        model.addAttribute("pageResult", myOrderService.getMyOrderWithPaging(memberId, requestDto, keyword));
        model.addAttribute("keyword", keyword);

        return "order/order_list";
    }

    @GetMapping("/order/order_view/orderId/{orderId}")
    public String orderViewPage(@PathVariable Long orderId,
                                @CurrentUser Member currentMember,
                                Model model) {


        return "order/order_view";
    }

}
