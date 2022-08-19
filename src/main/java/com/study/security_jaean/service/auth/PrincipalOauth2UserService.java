package com.study.security_jaean.service.auth;

import com.study.security_jaean.domain.user.User;
import com.study.security_jaean.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    /*
     * OAuth2User의 정보를 우리 서버 datebase에 등록
     *
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String provider = null;

        /*
         * super.loadUser(userRequest)
         * 엔드 포인트 결과 즉, OAuth2User 정보를 가진 객체를 리턴
         */
        OAuth2User oauth2User = super.loadUser(userRequest);

        //Provider 정보(클라이언트 아이디, 클라이언트 시크릿, 클라이언트 네임)
        ClientRegistration clientRegistration = userRequest.getClientRegistration(); //clientName

         // 실제 사용자 프로필 정보 (Map)
        Map<String, Object> attributes = oauth2User.getAttributes();

        log.error(">>>>> userRequest {}", clientRegistration); // 얘 중요
        log.error(">>>>> userRequest {}", attributes);


        provider = clientRegistration.getClientName();

        User user = getOAuth2User(provider, attributes);

        return new PrincipalDetails(user, attributes);
    }

    private User getOAuth2User(String provider, Map<String, Object> attributes) throws OAuth2AuthenticationException {
        User user = null;
        String id = null;
        String oauth2_id = null;
        Map<String, Object> response = null;

        if(provider.equalsIgnoreCase("google")) {
            response = attributes;
            id = (String) response.get("sub");
        } else if (provider.equalsIgnoreCase("naver")) {
            response = (Map<String, Object>) attributes.get("response");
            id = (String) response.get("id");
        } else {
            throw new OAuth2AuthenticationException("provider error!");
        }

        oauth2_id = provider + "_" + id;

        try {
            user = userRepository.findOAuth2UserByUsername(oauth2_id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OAuth2AuthenticationException("DATABASE error!");
        }

        if(user == null) {
            user = User.builder()
                    .user_name((String) response.get("name"))
                    .user_email((String) response.get("email"))
                    .user_id(oauth2_id)
                    .oauth_id(oauth2_id)
                    .user_password(new BCryptPasswordEncoder().encode(id))
                    .user_roles("ROLE_USER")
                    .user_provider(provider)
                    .build();

            try {
                userRepository.save(user);
                user = userRepository.findOAuth2UserByUsername(oauth2_id);
            } catch (Exception e) {
                e.printStackTrace();
                throw new OAuth2AuthenticationException("DATABASE error!");
            }
        }
// 시큐리티에서 oath2 login 설정할 떄 이떄 사용할 service를 넣어줘야하니까 만들어서 넣어주는거죠
        return user;
    }
}
