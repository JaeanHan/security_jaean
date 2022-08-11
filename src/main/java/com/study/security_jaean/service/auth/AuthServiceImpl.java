package com.study.security_jaean.service.auth;

import com.study.security_jaean.domain.user.UserRepository;
import com.study.security_jaean.web.dto.auth.SignupReqDto;
import com.study.security_jaean.web.dto.auth.UsernameCheckReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;

    @Override
    public boolean checkUsername(UsernameCheckReqDto usernameCheckReqDto) throws Exception {
        return userRepository.findUserByUsername(usernameCheckReqDto.getUsername()) == null;
    }

    @Override
    public boolean signup(SignupReqDto signupReqDto) throws Exception {
        return false;
    }
}
