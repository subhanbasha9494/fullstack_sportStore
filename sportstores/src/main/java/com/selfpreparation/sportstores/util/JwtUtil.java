package com.selfpreparation.sportstores.util;
import com.selfpreparation.sportstores.entity.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import com.selfpreparation.sportstores.constants.ApplicationConstant;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final Environment env;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateJwtToken(String name) {
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .issuer("Sport Store")
                .subject("JWT Token")
                .claim("username", name)
                .issuedAt(new java.util.Date())
                .expiration(new java.util.Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    // ✅ Validate JWT
    public boolean validateToken(String token) {
        try {
            String secret = env.getProperty(ApplicationConstant.JWT_SECRET_KEY,
                    ApplicationConstant.JWT_SECRET_DEFAULT_VALUE);
            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token); // throws if invalid/expired

            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // ✅ Extract Claims
    public Claims getClaims(String token) {
        String secret = env.getProperty(ApplicationConstant.JWT_SECRET_KEY,
                ApplicationConstant.JWT_SECRET_DEFAULT_VALUE);
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // ✅ Extract Username
    public String getUsernameFromToken(String token) {
        return getClaims(token).get("username", String.class);
    }

}
