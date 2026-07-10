package com.company.workforce.security;

import com.company.workforce.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Service
public class JwtService {

    private final JwtProperties jwtProperties;

    public JwtService(
            JwtProperties jwtProperties
    ) {
        this.jwtProperties = jwtProperties;
    }

    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                jwtProperties.secretKey()
                        .getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(
            String username
    ) {

        Date now = new Date();

        Date expiryDate =
                new Date(
                        now.getTime()
                                + jwtProperties.expiration()
                );

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(
            String token
    ) {

        return extractAllClaims(token)
                .getSubject();
    }

    public Date extractExpiration(
            String token
    ) {

        return extractAllClaims(token)
                .getExpiration();
    }

    public boolean isTokenValid(
            String token,
            UserDetails userDetails
    ) {

        String username =
                extractUsername(token);

        return username.equals(
                userDetails.getUsername()
        ) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(
            String token
    ) {

        return extractExpiration(token)
                .before(new Date());
    }

    private Claims extractAllClaims(
            String token
    ) {

        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}