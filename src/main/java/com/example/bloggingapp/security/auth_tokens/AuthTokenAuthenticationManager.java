package com.example.bloggingapp.security.auth_tokens;

import com.example.bloggingapp.users.dtos.UserPrincipalDTO;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthTokenAuthenticationManager implements AuthenticationManager {
    private final AuthTokenService authTokenService;

    private final ModelMapper modelMapper;

    public AuthTokenAuthenticationManager(AuthTokenService authTokenService,
                                          ModelMapper modelMapper) {
        this.authTokenService = authTokenService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof AuthTokenAuthentication) {
            AuthTokenAuthentication authTokenAuthentication = (AuthTokenAuthentication) authentication;
            String token = authTokenAuthentication.getCredentials();

            if (token != null) {
                try {
                    var user = authTokenService.getUser(token);
                    authTokenAuthentication.setPrinicipal(modelMapper.map(user, UserPrincipalDTO.class));
                    return authTokenAuthentication;
                } catch (AuthTokenService.InvalidTokenException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return null;
    }
}
