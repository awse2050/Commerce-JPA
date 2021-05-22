package com.awse.commerce.domains.member.dto;

import com.awse.commerce.domains.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Arrays;

@Getter
@Setter
@ToString
public class MemberDto extends User implements Serializable {

    private Member member;

    public MemberDto(Member member) {
        super(member.getEmail(), member.getPassword(), Arrays.asList(new SimpleGrantedAuthority(member.getRoleKey())));
        this.member = member;

    }
}
