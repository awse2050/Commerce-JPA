package com.awse.commerce.domains.util.config;


import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
// 로그인 인증을 대신한다. member(property)는 User 를 상속받은 클래스의 프로퍼티를 가져와야 함.
// User를 상속받은 객체의 데이터를 화면으로 뿌려주는 역할을 한다.
// 컨트롤러에서는 실제 객체타입으로 받아서 처리할수 있게 된다.
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : member")
public @interface CurrentUser {
}
