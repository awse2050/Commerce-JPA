package com.awse.commerce.domains.member.dto;


import com.awse.commerce.domains.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModifyMemberDto {

    // 사용자 번호, 이메일, 이름, 비밀번호, 주소(3), 핸드폰 번호
    private String email;

    private String name;

    private String password;

    private String phone;

    private String zipcode;

    private String extraAddress;

    private String detailsAddress;

}


