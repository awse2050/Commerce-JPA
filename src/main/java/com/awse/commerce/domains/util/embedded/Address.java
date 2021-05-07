package com.awse.commerce.domains.util.embedded;

import lombok.*;

import javax.persistence.Embeddable;

// daum의 주소API에 맞춘형식
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
@ToString
public class Address {

    private String zipcode;
    private String extraAddress;
    private String detailsAddress;

}
