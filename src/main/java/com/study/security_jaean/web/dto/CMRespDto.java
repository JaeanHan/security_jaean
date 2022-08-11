package com.study.security_jaean.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CMRespDto<T> {
    private int code;
    private String message;
    private T data;
}
