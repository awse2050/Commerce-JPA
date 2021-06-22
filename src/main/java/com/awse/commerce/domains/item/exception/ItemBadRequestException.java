package com.awse.commerce.domains.item.exception;

public class ItemBadRequestException extends RuntimeException {

    public ItemBadRequestException() {
        super("존재하지 않거나 삭제된 상품입니다.");
    }

    public ItemBadRequestException(String message) {
        super(message);
    }
}
