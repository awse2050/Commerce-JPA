package com.awse.commerce.domains.order.presentation;

import com.awse.commerce.domains.order.exception.OrderBadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderApiControllerAdvice {

    @ExceptionHandler(OrderBadRequestException.class)
    public ResponseEntity<String> orderExceptionHandler(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
