package com.example.bloggingapp.security.jwt;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JwtServiceTest {
    private final JwtService jwtService = new JwtService();

    private final String expToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0IiwiZXhwIjoxNjc3NzgwMzE0LCJpYXQiOjE2NzcxNzU1MTR9.wGbAz9tlqTPHUooIKygZ603Wu3IpVdaFb1Q07HfYb-4";

    @Test
    void createTokenTest() {
        Integer userId = 1234;
        String token = jwtService.createToken(userId,
                new Date(1677175514001L), new Date(1677780314003L));

        assertThat(token).isEqualTo(expToken);
    }

    @Test
    void verifyTokenTest() {
        assertThat(jwtService.getUserId(expToken)).isEqualTo(1234);
    }
}
