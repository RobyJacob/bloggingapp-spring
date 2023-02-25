package com.example.bloggingapp.security.auth_tokens;

import com.example.bloggingapp.users.dtos.UserPrincipalDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthTokenAuthentication implements Authentication {
    private final String token;

    private UserPrincipalDTO userPrincipalDto;

    public AuthTokenAuthentication(String token) {
        this.token = token;
    }

    public void setPrinicipal(UserPrincipalDTO userPrincipalDto) {
        this.userPrincipalDto = userPrincipalDto;
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
        return userPrincipalDto;
    }

    @Override
    public boolean isAuthenticated() {
        return userPrincipalDto != null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {}

    @Override
    public String getName() {
        return null;
    }
}
