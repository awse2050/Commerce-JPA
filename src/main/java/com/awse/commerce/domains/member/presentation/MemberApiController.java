package com.awse.commerce.domains.member.presentation;

import com.awse.commerce.domains.cart.service.CartService;
import com.awse.commerce.domains.member.dto.SignUpRequest;
import com.awse.commerce.domains.member.service.MemberService;
import com.awse.commerce.domains.member.validator.SignUpValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Log4j2
public class MemberApiController {

    private final CartService cartService;
    private final MemberService memberService;
    private final SignUpValidator signUpValidator;

    private final static String API_URI = "/api/member";

    @InitBinder("signUpRequest") // 네이밍 주의
    public void signupInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpValidator);
    }

    // 회원등록
    @PostMapping(API_URI)
    public ResponseEntity<String> signUpRequest(@RequestBody @Valid SignUpRequest signup,
                                              Errors errors) {
        if(errors.hasErrors()) {
            return new ResponseEntity<>(errors.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        // 회원가입 진행
        Long memberId =  memberService.signUp(signup);
        log.info("Signup Member ID : " +memberId);
        cartService.createCart(memberId);
        
       return new ResponseEntity<>("회원가입이 정상적으로 완료되었습니다.", HttpStatus.OK);
    }

}
