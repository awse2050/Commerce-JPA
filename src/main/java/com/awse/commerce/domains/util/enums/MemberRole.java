package com.awse.commerce.domains.util.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {
    USER("ROLE_USER", "일반회원"),
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String value;
}
