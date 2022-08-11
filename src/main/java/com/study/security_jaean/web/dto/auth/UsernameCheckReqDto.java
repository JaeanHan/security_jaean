package com.study.security_jaean.web.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UsernameCheckReqDto {
    @NotBlank
    @Size(min=4, max=16)
    private String username;
}
