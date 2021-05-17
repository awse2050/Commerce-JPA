package com.awse.commerce.domains.exception;

public class IsNotCartException extends RuntimeException {

    public IsNotCartException() {
        super("장바구니가 없습니다.");
    }

    public IsNotCartException(String message) {
        super(message);
    }

    public IsNotCartException(String message, Throwable cause) {
        super(message, cause);
    }

    public IsNotCartException(Throwable cause) {
        super(cause);
    }

    public IsNotCartException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
