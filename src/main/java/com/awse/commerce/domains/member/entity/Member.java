package com.awse.commerce.domains.member.entity;

import com.awse.commerce.domains.util.embedded.Address;
import com.awse.commerce.domains.util.entity.BaseEntity;

import javax.persistence.Embedded;

public class Member extends BaseEntity {

    private Long id;
    private String name;
    @Embedded
    private Address address; // 임베디드 타입을 사용해서 바꿀것.
}
