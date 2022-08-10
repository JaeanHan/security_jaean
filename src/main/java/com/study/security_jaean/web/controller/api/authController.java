package com.study.security_jaean.web.controller.api;

import com.study.security_jaean.service.auth.PrincipalDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class authController {

    private final PrincipalDetailService principalDetailService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup() {
        return ResponseEntity.ok(principalDetailService.addUser());
    }
}
