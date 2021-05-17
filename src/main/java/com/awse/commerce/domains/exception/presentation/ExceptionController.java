package com.awse.commerce.domains.exception.presentation;

import com.awse.commerce.domains.exception.IsNotCartException;
import com.awse.commerce.domains.exception.NotEnoughStockException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    // 복수개의 에러잡기
    @ExceptionHandler(value = {NotEnoughStockException.class, IsNotCartException.class})
    public String ExceptionHandler(RuntimeException e) {
        return e.getMessage();
    }

    // 하나의 에러잡기
//    @ExceptionHandler
//    public String NotEnoughStockExceptionHandler(NotEnoughStockException e) {
//        return e.getMessage();
//    }
}
