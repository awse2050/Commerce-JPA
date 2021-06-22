package com.awse.commerce.domains.cart.exception;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException() {
        super("장바구니가 존재하지 않습니다. 관리자에게 문의해주세요.");
    }

    public CartNotFoundException(String message) {
        super(message);
    }
}
