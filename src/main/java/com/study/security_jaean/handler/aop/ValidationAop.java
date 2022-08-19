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
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class ValidationAop {

    @Pointcut("@annotation(com.study.security_jaean.handler.aop.annotation.ValidCheck)")
    private void enableValid() {}

    @Before("enableValid()")
    public void validBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        for(Object arg : args) {
            if(arg.getClass() != BeanPropertyBindingResult.class) {
                continue;
            }

            BindingResult bindingResult = (BindingResult) arg;

            if(bindingResult.hasErrors()) {
                Map<String, String> errMap = new HashMap<>();

                for (FieldError fieldError : bindingResult.getFieldErrors()) {
                    errMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                }
                throw new CustomValidationApiException("유효성 검사 실패", errMap);
            }
            break;
        }
    }

    @AfterReturning(value="enableValid()", returning = "o")
    public void AfterValidation(JoinPoint joinPoint, Object o) {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("유효성 검사 완료 : {}", o);
    }
}
