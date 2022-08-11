package com.study.security_jaean.service.auth;

import com.study.security_jaean.web.dto.auth.SignupReqDto;
import com.study.security_jaean.web.dto.auth.UsernameCheckReqDto;

public interface AuthService {
    public boolean checkUsername(UsernameCheckReqDto usernameCheckReqDto) throws Exception;
    public boolean signup(SignupReqDto signupReqDto) throws Exception;
}
