package com.study.security_jaean.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {
    private int user_code;
    private String user_name;
    private String user_email;
    private String user_id;
    private String oauth_id;
    @JsonIgnore
    private String user_password;
    private String user_roles;
    private String user_provider;

    private String user_profile_img;
    private String user_address;
    private String user_phone;
    private int user_gender;

    public List<String> getUserRoles() {
        if(user_roles == null || user_roles.isBlank()) return new ArrayList<String>(); // null 일 수 없다 grantedAuthority
        return Arrays.asList(user_roles.replaceAll(" ", "").split(","));
    }
}
