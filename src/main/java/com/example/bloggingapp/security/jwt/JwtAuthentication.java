package com.example.bloggingapp.security.jwt;

import com.example.bloggingapp.users.dtos.UserPrincipalDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.security.auth.Subject;
import java.io.Serializable;
import java.util.Collection;

public class JwtAuthentication implements Authentication, Serializable {
    private final String token;

    private UserPrincipalDTO userPrincipalDTO;

    public JwtAuthentication(String token) {
        this.token = token;
    }

    public void setPrincipal(UserPrincipalDTO userPrincipalDTO) {
        this.userPrincipalDTO = userPrincipalDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public UserPrincipalDTO getPrincipal() {
        return userPrincipalDTO;
    }

    @Override
    public boolean isAuthenticated() {
        return userPrincipalDTO != null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean implies(Subject subject) {
        return Authentication.super.implies(subject);
    }
}
