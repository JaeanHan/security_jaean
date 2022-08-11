package com.study.security_jaean.handler;

import com.study.security_jaean.handler.exception.CustomValidationApiException;
import com.study.security_jaean.web.dto.CMRespDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController //얘가 본체
@ControllerAdvice
public class RestControllerExceptionHandler {
    //SerializeUID

    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationError(CustomValidationApiException e) {
        return ResponseEntity.badRequest().body(new CMRespDto<>(-1, e.getMessage(), e.getErrMap()));
    }

}
