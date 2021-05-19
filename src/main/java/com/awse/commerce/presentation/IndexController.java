package com.awse.commerce.presentation;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class IndexController {

    @GetMapping("/")
    public String index() {
        log.info("index page..");
        return "index";
    }
}
