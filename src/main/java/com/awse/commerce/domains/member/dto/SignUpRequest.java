package com.awse.commerce.domains.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class SignUpRequest {
    // 이메일, 이름, 비밀번호, 주소( 3가지 )
    @Email
    @NotBlank
    private String email;

    @Length(min = 2)
    @NotBlank
    private String name;

    @Length(min = 8, message = "최소 8자 이상 입력해주세요.")
    @NotBlank
    private String password;

    @Length(min = 8, message = "최소 8자 이상 입력해주세요.")
    @NotBlank
    private String confirmPassword;

    @NotEmpty
    private String zipcode;

    @NotEmpty
    private String extraAddress;

    @NotEmpty
    private String detailsAddress;

    private boolean verifyPassword;

    @Builder
    public SignUpRequest(String email,
                         String name,
                         String password,
                         String confirmPassword,
                         String zipcode,
                         String extraAddress,
                         String detailsAddress) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.confirmPassword = confirmPassword;
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
