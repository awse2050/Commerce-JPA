package com.awse.commerce.domains.util.embedded;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

// daum의 주소API에 맞춘형식
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Address {

    private String zipcode;
    private String extraAddress;
    private String detailsAddress;

}
