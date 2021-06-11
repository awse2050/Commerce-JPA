package com.awse.commerce.bootpay;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Log4j2
public class BootPayController {

    // 결제 성공 및 실패 후 이동페이지
    @GetMapping("/pay-result/{resPayment}")
    public String movePaymentResultPage(@PathVariable("resPayment") String resMsg,
                                        Model model) {
       log.info("resMsg : " + resMsg);

       model.addAttribute("result", resMsg);
        return "pay-result";
    }

}
