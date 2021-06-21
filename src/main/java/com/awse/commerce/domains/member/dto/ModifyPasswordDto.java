package com.awse.commerce.domains.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ModifyPasswordDto {

    @Length(min = 8, message = "현재 비밀번호를 최소 8자 이상 입력해주세요.")
    @NotBlank(message = "현재 비밀번호를 입력하세요.")
    private String currentPassword;

    @Length(min = 8, message = "변경할 비밀번호를 최소 8자 이상 입력해주세요.")
    @NotBlank(message = "변경할 비밀번호를 입력하세요.")
    private String toModifyPassword;

    @Length(min = 8, message = "비밀번호 확인란에 최소 8자 이상 입력해주세요.")
    @NotBlank(message = "비밀번호 확인란을 입력하세요.")
    private String confirmPassword;

}
