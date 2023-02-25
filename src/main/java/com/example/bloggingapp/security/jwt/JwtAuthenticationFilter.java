package com.example.bloggingapp.security.jwt;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationFilter extends AuthenticationFilter {
    private final JwtAuthenticationManager authenticationManager;

    private final JwtAuthenticationConverter authenticationConverter;

    public JwtAuthenticationFilter(JwtAuthenticationManager authenticationManager,
                                   JwtAuthenticationConverter authenticationConverter) {
        super(authenticationManager, authenticationConverter);

        this.authenticationManager = authenticationManager;

        this.authenticationConverter = authenticationConverter;

        setSuccessHandler((request, response, authentication) -> {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        });
    }
}
