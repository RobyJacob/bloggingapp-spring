package com.example.bloggingapp.security.auth_tokens;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthTokenAuthenticationConverter implements AuthenticationConverter {
    @Override
    public Authentication convert(HttpServletRequest request) {
        if (request.getHeader("X-Auth-Token") != null) {
            String token = request.getHeader("X-Auth-Token");

            return new AuthTokenAuthentication(token);
        }

        return null;
    }
}
