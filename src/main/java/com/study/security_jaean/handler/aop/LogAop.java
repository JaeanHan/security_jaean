package com.study.security_jaean.handler.aop;

import org.aspectj.apache.bcel.classfile.Code;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class LogAop {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogAop.class);

    @Pointcut("@annotation(com.study.security_jaean.handler.aop.annotation.Log)")
    private void enableLog() {}

    @Around("enableLog()")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        Map<String, Object> params = getParams(joinPoint);

        LOGGER.info(">>>>> Method call >>>>> {}({})",
                joinPoint.getSignature().getName(),
                params);

        Object result = joinPoint.proceed();

        LOGGER.info(">>>>> Method end >>>>> {} --return--> ({})",
                joinPoint.getSignature().getName(),
                result);

        return result;
    }

    private Map<String, Object> getParams(ProceedingJoinPoint joinPoint) {
        Map<String, Object> params = new HashMap<>();

        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        String[] argNames = codeSignature.getParameterNames();

        for(int i=0; i < argNames.length; i++) {
            params.put(argNames[i], args[i]);
        }

        return params;
    }
}
