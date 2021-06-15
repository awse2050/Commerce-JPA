package com.awse.commerce.domains.order.presentation;

import com.awse.commerce.bootpay.BootpayApi;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.order.dao.OrderRequestDao;
import com.awse.commerce.domains.order.service.OrderService;
import com.awse.commerce.domains.util.config.security.CurrentUser;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
@PropertySource("classpath:application-pay.properties")
public class OrderApiController {

    // 검증에 필요한 applicationId
    @Value("${bootpay.verify.application.id}")
    private String application_id;

    @Value("${bootpay.verify.private.key}")
    private String private_key;

    private final OrderService orderService;

    @PostMapping("/api/order")
    public ResponseEntity<String> orderRequest(@RequestBody OrderRequestDao orderDao,
                                               @CurrentUser Member currentMember) throws Exception {
        log.info("orderDao : " + orderDao);
        String paymentResult = verifyPayment(orderDao.getReceiptId(), orderDao.getPrice());

        if(!paymentResult.equals("OK")) {
            return new ResponseEntity<>(paymentResult, HttpStatus.BAD_REQUEST);
        }

        Long memberId = currentMember.getId();

        Long orderId = orderService.order(memberId, orderDao);

        return new ResponseEntity<>(paymentResult, HttpStatus.OK);
    }

    private String verifyPayment(String receiptId, int price) throws Exception {
        String result = "";

        BootpayApi bootpayApi = new BootpayApi(application_id, private_key);
        bootpayApi.getAccessToken();

        try {
            HttpResponse res = bootpayApi.verify(receiptId);
            String resJson = IOUtils.toString(res.getEntity().getContent(), "UTF-8");

            JsonObject resAll = parseToJsonObject(resJson);// 전체 데이터
            JsonObject resDetails = parseToJsonObject(resAll.get("data").toString()); // obj.data

            int bootStatus = resAll.get("status").getAsInt();
            int bootStatusRes = resDetails.get("status").getAsInt();
            int bootPayMoney = resDetails.get("price").getAsInt();
//            String bootReceiptId = resDetails.get("receipt_id").getAsString();

            // 거래 상태(완료)
            if (bootStatus == 200) {
                // 결제된 금액 ?== 결제할 가격
                result = (bootPayMoney == price) && (bootStatusRes == 1) ? "OK" : "NO";
            }
        } catch (Exception e) {
            result = e.getMessage();
        }

        return result;
    }

    private JsonObject parseToJsonObject(String str) {
        // new JsonParser => Deprecated
        return JsonParser.parseString(str).getAsJsonObject();
    }
}
