package com.awse.commerce.domains.member.presentation;

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
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Log4j2
public class MemberApiController {

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
        // save 반환타입 추후 변경
        Long memberId =  memberService.signUp(signup);
        
       return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
