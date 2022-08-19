package com.study.security_jaean.web.controller.api;

import com.study.security_jaean.handler.aop.annotation.Log;
import com.study.security_jaean.handler.aop.annotation.Timer;
import com.study.security_jaean.handler.aop.annotation.ValidCheck;
import com.study.security_jaean.handler.aop.annotation.ValidCheck2;
import com.study.security_jaean.service.auth.AuthService;
import com.study.security_jaean.service.auth.PrincipalDetailService;
import com.study.security_jaean.service.auth.PrincipalDetails;
import com.study.security_jaean.web.dto.CMRespDto;
import com.study.security_jaean.web.dto.auth.SignupReqDto;
import com.study.security_jaean.web.dto.auth.UsernameCheckReqDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final PrincipalDetailService PrincipalDetailService;
    private final AuthService authService;

    @Timer
    @Log
    @ValidCheck
    @GetMapping("/signup/validation/username")
    public ResponseEntity<?> checkUsername(@Valid UsernameCheckReqDto usernameCheckReqDto, BindingResult bindingResult) {
        boolean status;

        try {
            status = authService.checkUsername(usernameCheckReqDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "서버 오류", false));
        }

        return ResponseEntity.ok(new CMRespDto<>(1, "회원가입 가능여부", status));
    }

    @ValidCheck
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupReqDto signupReqDto, BindingResult bindingResult) {
        boolean status;

        try {
            status = PrincipalDetailService.addUser(signupReqDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "회원가입 실패", false));
        }

        return ResponseEntity.ok(new CMRespDto<>(1, "회원가입 성공", status));
    }

    @GetMapping("principal")
    public ResponseEntity getPrincipal(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        if(principalDetails == null) {
            return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "principal is null", null));
        }
        return ResponseEntity.ok(new CMRespDto<>(1, "success load", principalDetails.getUser() ));
    }
}