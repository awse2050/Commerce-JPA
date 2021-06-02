package com.awse.commerce.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class LogAdvice {

    @Around("execution(* com.awse.commerce.domains..*.*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        long time = System.currentTimeMillis();

        Object result = pjp.proceed();

        System.out.println(" Execute To " +pjp.getSignature() + "executed.. " + (System.currentTimeMillis() - time));
        return result;
    }

}
