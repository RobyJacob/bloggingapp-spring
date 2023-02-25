package com.example.bloggingapp.security.jwt;

import com.example.bloggingapp.users.UserService;
import com.example.bloggingapp.users.dtos.UserPrincipalDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@Primary
public class JwtAuthenticationManager implements AuthenticationManager {
    private final JwtService jwtService;

    private final UserService userService;

    private final ModelMapper modelMapper;

    public JwtAuthenticationManager(JwtService jwtService, UserService userService, ModelMapper modelMapper) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof JwtAuthentication) {
            JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
            String token = jwtAuthentication.getCredentials();

            if (token != null) {
                Integer userId = jwtService.getUserId(token);
                jwtAuthentication.setPrincipal(modelMapper.map(userService.getUserById(userId),
                        UserPrincipalDTO.class));
                return jwtAuthentication;
            }
        }
        return null;
    }
}
