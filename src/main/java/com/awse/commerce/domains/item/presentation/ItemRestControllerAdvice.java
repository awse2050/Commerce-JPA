package com.awse.commerce.domains.item.presentation;

import com.awse.commerce.domains.item.exception.ItemBadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ItemRestControllerAdvice {

    @ExceptionHandler(ItemBadRequestException.class)
    public ResponseEntity<String> itemExceptionHandler(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


}

