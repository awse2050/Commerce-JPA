package com.awse.commerce.domains.member.dto;


import com.awse.commerce.domains.member.entity.Member;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModifyMemberDto {

    // 사용자 번호, 이메일, 이름, 비밀번호, 주소(3), 핸드폰 번호
    @Email(message = "이메일 형식으로 작성해주세요.")
    private String email;

    @Length(min = 2, message = "이름을 2글자 이상 작성하세요.")
    @NotBlank
    private String name;

    @Length(min = 10, message = "핸드폰 번호를 최소 10자 이상 입력하세요.")
    @NotBlank(message = "핸드폰 번호를 정확하게 입력해주세요.")
    private String phone;

    @NotEmpty(message = "우편번호를 검색하세요.")
    private String zipcode;

    @NotEmpty(message = "우편번호를 검색하세요.")
    private String extraAddress;

    @NotEmpty(message = "상세주소를 입력하세요.")
    private String detailsAddress;

}


