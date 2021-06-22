package com.awse.commerce.domains.like.exception;

public class LikeBadRequestException extends RuntimeException {

    public LikeBadRequestException() {
        super("좋아요 요청에 실패하였습니다.");
    }

    public LikeBadRequestException(String msg) {
        super(msg);
    }

}
