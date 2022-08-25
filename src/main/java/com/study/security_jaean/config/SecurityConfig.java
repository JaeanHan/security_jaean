package com.study.security_jaean.config;

import com.study.security_jaean.config.auth.AuthFailHandler;
import com.study.security_jaean.service.auth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity // 기존의 WebSecurityConfigurerAdapter 비활성화하고 현제 시큐리티 설정을 따르겠다.
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PrincipalOauth2UserService principalOauth2UserService;

    private final CorsFilter corsFilter;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
//                .addHeaderWriter(new StaticHeadersWriter("X-FRAME-OPTIONS", "ALLOW-FROM /**"));
//        http.addFilter(corsFilter);
        http.authorizeRequests()
                .antMatchers("/api/v1/grant/test/user/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/grant/test/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")

                .antMatchers("/notice/addition", "notice/modification/**")
                .access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
//                .hasRole("ADMIN")

                .antMatchers("/api/v1/grant/test/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .antMatchers("/", "/index", "/mypage/**", "/notice/addition", "/notice/modification/**") //우리가 지정한 요청
                .authenticated()                            // 인증을 거쳐라
                .anyRequest()                               // 외 모든 요청은
                .permitAll()                                // 모든 권한을 허용한다.
                .and()
                .formLogin()                                // 로그인 방식은 폼로그인을 사용
                .loginPage("/auth/signin")                  // 로그인 페이지는 해당 get 요청을 통해
                .loginProcessingUrl("/auth/signin")         // 로그인 요청 (post 요청)
                .failureHandler(new AuthFailHandler())
                .and()
                .oauth2Login()
                .userInfoEndpoint() // oauth2 login에 있어서 진짜 중요
                /*
                 * 1. google, naver, kakao 로그인 요청 -> 코드를 발급해줌
                 * 2. 발급받은 코드를 가진 상태로 권한을 요청 (토큰 발급 요청)
                 * 3. 발급받은 토큰을 통해 인증을 거치면 스코프에 등록된 프로필 정보를 가져 올 수 있게됨
                 * 4. 해당 정보를 시큐리티의 객체로 전달받음.
                 */
                .userService(principalOauth2UserService)
                .and()
                .defaultSuccessUrl("/")
                .failureHandler(null);


    }
}
