package com.awse.commerce.domains.member.presentation;

import com.awse.commerce.domains.cart.service.CartService;
import com.awse.commerce.domains.member.dto.ModifyMemberDto;
import com.awse.commerce.domains.member.dto.SignUpRequest;
import com.awse.commerce.domains.member.entity.Member;
import com.awse.commerce.domains.member.service.MemberService;
import com.awse.commerce.domains.member.validator.SignUpValidator;
import com.awse.commerce.domains.util.config.security.CurrentUser;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation(value = "회원가입", notes = "회원가입하기")
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

    // 회원수정
    @ApiOperation(value = "회원수정", notes = "회원 정보 변경하기")
    @PutMapping(API_URI)
    public ResponseEntity<String> modifyRequest(@RequestBody ModifyMemberDto modifyMemberDto,
                              @CurrentUser Member currentMember) {

        if(currentMember == null) {
            return new ResponseEntity<>("로그인이 필요한 서비스입니다.",HttpStatus.BAD_REQUEST);
        }
        Long memberId = currentMember.getId();

        memberService.modifyMemberInfo(memberId, modifyMemberDto);

        return new ResponseEntity<>("회원정보 변경 완료",  HttpStatus.OK);
    }

}
