package com.awse.commerce.domains.cart.presentation;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class CartApiControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> countExceptionHandler(MethodArgumentNotValidException ex) {

        return ResponseEntity.badRequest().body(ex.getFieldError().getDefaultMessage());
    }
}
