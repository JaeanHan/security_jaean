package com.study.security_jaean.service.auth;

import com.study.security_jaean.domain.user.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {
    // serialVersionUID

    private User user;
    private Map<String, Object> attributes;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities= new ArrayList<>();
        List<String> roleList = user.getUserRoles();

        roleList.forEach(role -> {
            grantedAuthorities.add(() -> role);
        });

        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getUser_password();
    }

    @Override
    public String getUsername() {
        return user.getUser_id();
    }

    /*
    계정 만료
    true : 만료x
    false : 만료o
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*
    계정 잠김
    true : 잠김x
    false : 잠김o
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*
    비밀번호 만료
    true: 만료x
    false : 만료o
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*
    사용자 활성화 여부
    true : 활성화 o
    false : 활성화 x
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return user.getUser_name();
    }
}
