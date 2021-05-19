package com.awse.commerce.domains.member.validator;

import com.awse.commerce.domains.member.dto.SignUpRequest;
import com.awse.commerce.domains.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@RequiredArgsConstructor
@Component
@Log4j2
public class SignUpValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(SignUpRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SignUpRequest signupRequest = (SignUpRequest) target;

        if(!verifyPassword(signupRequest)) {
            errors.rejectValue("password", "NOT_CORRECT_PASSWORD","비밀번호가 일치하지 않습니다.");
        } else if(existEmail(signupRequest)) {
            errors.rejectValue("email", "EXIST_EMAIL","이미 존재하는 이메일입니다.");
        }
    }

    private boolean verifyPassword(SignUpRequest signupRequest) {
        return signupRequest.isVerifyPassword();
    }

    private boolean existEmail(SignUpRequest signupRequest) {
       return memberRepository.existsByEmail(signupRequest.getEmail());
    }
}
