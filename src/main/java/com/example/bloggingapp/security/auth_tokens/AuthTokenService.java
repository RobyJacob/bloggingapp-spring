package com.example.bloggingapp.security.auth_tokens;

import com.example.bloggingapp.users.UserEntity;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import javax.security.auth.login.CredentialNotFoundException;
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

    public UserEntity getUser(String token) throws InvalidTokenException {
        var authTokenEntity = authTokenRepository.findById(UUID.fromString(token));

        if (authTokenEntity.isEmpty()) throw new InvalidTokenException("No token found");

        return authTokenEntity.get().getUser();
    }

    static class InvalidTokenException extends CredentialException {
        public InvalidTokenException(String message) {
            super(message);
        }
    }
}
