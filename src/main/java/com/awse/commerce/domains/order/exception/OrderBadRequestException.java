package com.awse.commerce.domains.order.exception;

public class OrderBadRequestException extends RuntimeException {

    public OrderBadRequestException() {
        super();
    }

    public OrderBadRequestException(String msg) {
        super(msg);
    }

}
