package com.awse.commerce.domains.member.dto;

import com.awse.commerce.domains.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

@Getter
@Setter
@ToString
public class MemberDto extends User implements Serializable, OAuth2User {

    private Member member;
    private Map<String, Object> attr;

    public MemberDto(Member member) {
        super(member.getEmail(), member.getPassword(), Arrays.asList(new SimpleGrantedAuthority(member.getRoleKey())));
        this.member = member;

    }

    // Google OAuth2
    public MemberDto(Member member, Map<String, Object> attr) {
        super(member.getEmail(), member.getPassword(), Arrays.asList(new SimpleGrantedAuthority(member.getRoleKey())));
        this.member = member;
        this.attr = attr;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }

    @Override
    public String getName() {
        return this.member.getName();
    }
}
