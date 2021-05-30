package com.awse.commerce.bootpay.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VerifyResult {
    // 결제완료된상태(코드), 결제의 결과, 실제 결제된 금액, 고유영수증 키
    private int status;
    private int statusRes;
    private int price;
    private String receipt_id;

    @Builder
    protected VerifyResult(int status, int statusRes, int price, String receipt_id) {
        this.status = status;
        this.statusRes = statusRes;
        this.price = price;
        this.receipt_id = receipt_id;
    }

}
