package com.study.security_jaean.service.auth;

import com.study.security_jaean.domain.user.User;
import com.study.security_jaean.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

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

    public boolean addUser() {
        User user = User.builder()
                .user_name("한재안")
                .user_email("jaean1999@naver.com")
                .user_id("tt")
                .user_password(new BCryptPasswordEncoder().encode("1234"))
                .user_roles("ROLE_USER, ROLE_MANAGER")
                .build();

        try {
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
