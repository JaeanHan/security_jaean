package com.study.security_jaean.config;

import com.study.security_jaean.config.auth.AuthFailHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity // 기존의 WebSecurityConfigurerAdapter 비활성화하고 현제 시큐리티 설정을 따르겠다.
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/api/v1/grant/test/user/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/grant/test/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/grant/test/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .antMatchers("/", "/index", "/mypage/**") //우리가 지정한 요청
                .authenticated()                            // 인증을 거쳐라
                .anyRequest()                               // 외 모든 요청은
                .permitAll()                                // 모든 권한을 허용한다.
                .and()
                .formLogin()                                // 로그인 방식은 폼로그인을 사용
                .loginPage("/auth/signin")                  // 로그인 페이지는 해당 get 요청을 통해
                .loginProcessingUrl("/auth/signin")         // 로그인 요청 (post 요청)
                .failureHandler(new AuthFailHandler())
                .defaultSuccessUrl("/");
    }
}
