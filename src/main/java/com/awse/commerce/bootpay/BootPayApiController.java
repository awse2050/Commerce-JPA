package com.awse.commerce.bootpay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@PropertySource("classpath:application-pay.properties")
public class BootPayApiController {

    @Value("${bootpay.application.Id}")
    private String application_id;

    @Value("${bootpay.private.key}")
    private String private_key;

    @GetMapping("/receipt/{receipt_id}")
    public ResponseEntity<String> get(@PathVariable("receipt_id")String receiptId) throws Exception {
        BootpayApi bootpayApi = new BootpayApi(application_id, private_key);

        log.info("receipt Id : " + receiptId);
        bootpayApi.getAccessToken();

        log.warn(bootpayApi.getToken());

        try {
            HttpResponse res = bootpayApi.verify(receiptId);
            String str = IOUtils.toString(res.getEntity().getContent(), "UTF-8");
            System.out.println(str);

            return new ResponseEntity<>(str, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }

    }

}
