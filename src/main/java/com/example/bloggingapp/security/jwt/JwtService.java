package com.example.bloggingapp.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private final Algorithm algorithm = Algorithm.HMAC256("secret key");

    public String createToken(Integer userId) {
        return createToken(userId,
                new Date(),
                new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7));
    }

    protected String createToken(Integer userId, Date issuedDate, Date expiryDate) {
        return JWT.create()
                .withSubject(userId.toString())
                .withIssuedAt(issuedDate)
                .withExpiresAt(expiryDate)
                .sign(algorithm);
    }

    public Integer getUserId(String token) {
        var verifier = JWT.require(algorithm).build();
        var decodedJWT = verifier.verify(token);
        return Integer.parseInt(decodedJWT.getSubject());
    }
}
