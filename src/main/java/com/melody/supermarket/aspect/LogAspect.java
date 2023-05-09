package com.melody.supermarket.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect {
    @Pointcut("execution(* com.melody.supermarket.controller.*.*(..))")
    public void ControllerPointCut() {}
    @Before("ControllerPointCut()")
    public void before(JoinPoint jp) {
        log.info(jp.getThis().getClass().getSimpleName() + "." + jp.getSignature().getName()+"()");
    }

    @AfterThrowing(value = "ControllerPointCut()", throwing = "ex")
    public void throwAdvice(JoinPoint jp, Throwable ex) {
        log.error(jp.getThis().getClass().getSimpleName() + "." + jp.getSignature().getName() + "()\t" + ex.getMessage());
    }
}
