package com.awse.commerce.domains.member.presentation;

import com.awse.commerce.domains.member.exception.MemberBadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberApiControllerAdvice {

    @ExceptionHandler(MemberBadRequestException.class)
    public ResponseEntity<String> memberExceptionHandler(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
