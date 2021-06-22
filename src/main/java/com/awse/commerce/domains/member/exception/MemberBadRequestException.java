package com.awse.commerce.domains.member.exception;

public class MemberBadRequestException extends RuntimeException {

    public MemberBadRequestException() {
        super();
    }

    public MemberBadRequestException(String msg) {
        super(msg);
    }

}
