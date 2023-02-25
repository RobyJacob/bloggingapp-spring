package com.example.bloggingapp.security.auth_tokens;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class AuthTokenAuthenticationFilter extends AuthenticationFilter {
    private final AuthTokenAuthenticationConverter authTokenAuthenticationConverter;

    private final AuthTokenAuthenticationManager authTokenAuthenticationManager;

    public AuthTokenAuthenticationFilter(AuthTokenAuthenticationManager authenticationManager,
                                         AuthTokenAuthenticationConverter authenticationConverter) {
        super(authenticationManager, authenticationConverter);

        this.authTokenAuthenticationConverter = authenticationConverter;
        this.authTokenAuthenticationManager = authenticationManager;

        setSuccessHandler(((request, response, authentication) -> {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }));
    }
}
