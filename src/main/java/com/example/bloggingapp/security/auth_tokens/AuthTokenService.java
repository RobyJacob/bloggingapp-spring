package com.example.bloggingapp.security.auth_tokens;

import com.example.bloggingapp.users.UserEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthTokenService {
    private final AuthTokenRepository authTokenRepository;

    public AuthTokenService(AuthTokenRepository authTokenRepository) {
        this.authTokenRepository = authTokenRepository;
    }

    public UUID createAuthToken(UserEntity user) {
        AuthTokenEntity authTokenEntity = new AuthTokenEntity();
        authTokenEntity.setUser(user);
        AuthTokenEntity savedAuthToken = authTokenRepository.save(authTokenEntity);
        return savedAuthToken.getId();
    }
}
