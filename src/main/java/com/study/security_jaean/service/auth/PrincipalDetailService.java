package com.study.security_jaean.service.auth;

import com.study.security_jaean.domain.user.User;
import com.study.security_jaean.domain.user.UserRepository;
import com.study.security_jaean.web.dto.auth.SignupReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username : " + username);
        User userEntity = null;
        try {
            userEntity = userRepository.findUserByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException(username + " 을 찾을 수 없습니다.");
        }

        if(userEntity == null) {
            throw new UsernameNotFoundException(username + " 사용자이름은 사용 할 수 없습니다.");
        }

        return new PrincipalDetails(userEntity);
    }

    public boolean addUser(@Valid SignupReqDto signupReqDto) throws Exception {
        return userRepository.save(signupReqDto.toEntity()) > 0;
    }
}
