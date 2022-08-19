package com.study.security_jaean.handler.aop;

import com.study.security_jaean.handler.exception.CustomValidationApiException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class ValidationAop2 {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(com.study.security_jaean.handler.aop.annotation.ValidCheck2)")
    private void enableValid() {}

    @Before("enableValid()")
    public void Before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        LOGGER.info(">>>>> 유효성 검사 중. . .");

        for(Object arg : args) {
            if(arg.getClass() == BeanPropertyBindingResult.class) {
                BindingResult bindingResult = (BindingResult) arg;

                if(bindingResult.hasErrors()) {
                    Map<String, String> map = new HashMap<>();

                    bindingResult.getFieldErrors().forEach(error -> {
                        map.put(error.getField(), error.getDefaultMessage());
                    });

                    throw new CustomValidationApiException("유효성 검사 실패", map);
                }
            }
        }
    }

    @AfterReturning(value = "enableValid()", returning = "o")
    public void After(JoinPoint joinPoint, Object o) {
        LOGGER.info("유효성 검사 완료: {}", o);

    }
}
