package com.awse.commerce.domains.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Setter
@Getter
public class SignUpRequest {
    // 이메일, 이름, 비밀번호, 주소( 3가지 )
    @Email(message = "이메일 형식으로 입력하세요.")
    @NotBlank(message = "이메일을 입력하세요.")
    private String email;

    @Length(min = 2, message = "이름을 최소 2자 이상 입력해주세요.")
    @NotBlank(message = "이름을 입력하세요.")
    private String name;

    @Length(min = 8, message = "비밀번호를 최소 8자 이상 입력해주세요.")
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;

    @Length(min = 8, message = "2차 비밀번호를 최소 8자 이상 입력해주세요.")
    @NotBlank(message = "2차 비밀번호를 정확히 입력하세요.")
    private String confirmPassword;

    @Length(min = 10, message = "핸드폰 번호를 최소 10자 이상 입력하세요.")
    @NotBlank(message = "핸드폰 번호를 정확하게 입력해주세요.")
    private String phone;

    @NotEmpty(message = "우편번호를 검색하세요.")
    private String zipcode;

    @NotEmpty(message = "우편번호를 검색하세요.")
    private String extraAddress;

    @NotEmpty(message = "상세주소를 입력하세요.")
    private String detailsAddress;

    private boolean verifyPassword;

    @Builder
    public SignUpRequest(String email,
                         String name,
                         String password,
                         String confirmPassword,
                         String phone,
                         String zipcode,
                         String extraAddress,
                         String detailsAddress) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phone = phone;
        this.zipcode = zipcode;
        this.extraAddress = extraAddress;
        this.detailsAddress = detailsAddress;
        this.verifyPassword = verifyPassword();
    }

    // 비밀번호 동일여부 체크
    private boolean verifyPassword() {
        return this.getPassword().equals(this.getConfirmPassword());
    }

}
