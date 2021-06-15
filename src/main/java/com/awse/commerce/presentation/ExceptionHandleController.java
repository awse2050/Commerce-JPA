package com.awse.commerce.presentation;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
@Log4j2
public class ExceptionHandleController implements ErrorController {

    private final String ERROR_404_URI = "error/404";
    private final String ERROR_500_URI = "error/500";

    @GetMapping("/error")
    public String errorHandle(HttpServletRequest request, Model model) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        log.info(status);
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));

        if(status != null) {
            int statusCode = Integer.valueOf(status.toString());

            log.info(statusCode);

            if( statusCode == HttpStatus.NOT_FOUND.value() ) {
                // 표시할 정보
                model.addAttribute("code", status.toString());
                model.addAttribute("errorMsg", "요청하신 페이지는 없는 페이지입니다.");
                return ERROR_404_URI;
            } else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {

                model.addAttribute("errorMsg", "잘못된 요청입니다. 관리자에게 문의주세요.");
                return ERROR_500_URI;
            }
        }

        return null;
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}
