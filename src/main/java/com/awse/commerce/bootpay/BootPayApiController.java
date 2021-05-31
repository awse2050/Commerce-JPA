package com.awse.commerce.bootpay;

import com.awse.commerce.bootpay.model.response.VerifyResult;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
@PropertySource("classpath:application-pay.properties")
public class BootPayApiController {

    @Value("${bootpay.application.Id}")
    private String application_id;

    @Value("${bootpay.private.key}")
    private String private_key;

    // 결제 검증
    @GetMapping("/receipt/{receipt_id}/price/{price}")
    public ResponseEntity<String> get(@PathVariable("receipt_id")String receiptId,
                                        @PathVariable("price")int price) throws Exception {
        BootpayApi bootpayApi = new BootpayApi(application_id, private_key);

        log.info("receipt Id : " + receiptId); // 고유번호
        log.info("price : " + price); // 결제되야할 가격
        bootpayApi.getAccessToken();

        String result = "NO";
        try {
            HttpResponse res = bootpayApi.verify(receiptId);
            String resJson = IOUtils.toString(res.getEntity().getContent(), "UTF-8");
            System.out.println(resJson);

            JsonObject resAll = parseToJsonObject(resJson);// 전체 데이터
            JsonObject resDetails = parseToJsonObject(resAll.get("data").toString()); // obj.data

            int bootStatus = resAll.get("status").getAsInt();
            int bootStatusRes = resDetails.get("status").getAsInt();
            int bootPayMoney = resDetails.get("price").getAsInt();
            String bootReceiptId = resDetails.get("receipt_id").getAsString();

            // 거래 상태(완료)
            if(bootStatus == 200) {
                // 결제된 금액 ?== 결제할 가격
               result = (bootPayMoney == price) && (bootStatusRes == 1) ? "OK" : "NO";
             }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private JsonObject parseToJsonObject(String str) {
        // new JsonParser => Deprecated
        return JsonParser.parseString(str).getAsJsonObject();
    }

}
