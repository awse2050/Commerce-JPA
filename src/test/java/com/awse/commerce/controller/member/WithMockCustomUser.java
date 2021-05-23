package com.awse.commerce.controller.member;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCutstomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    String email() default "user1@aaa.com";
}