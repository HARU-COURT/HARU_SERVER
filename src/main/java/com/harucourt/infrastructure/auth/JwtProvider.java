package com.harucourt.infrastructure.auth;

import com.harucourt.domain.auth.domain.Token;
import com.harucourt.domain.auth.domain.type.TokenType;
import com.harucourt.domain.auth.exception.EmptyTokenException;
import com.harucourt.domain.auth.exception.InvalidTokenException;
import com.harucourt.infrastructure.persistence.auth.TokenRepository;
import com.harucourt.shared.config.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private final TokenRepository tokenRepository;

    private SecretKey key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(UUID uuid, String email, String username) {
        Date now = new Date();
        Header header = Jwts.header()
                .type("JWT")
                .build();
        Claims claims = Jwts.claims()
                .subject(uuid.toString())
                .add("email", email)
                .add("name", username)
                .add("type", TokenType.ACCESS_TOKEN)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + jwtProperties.getAccessExpirationTime()))
                .build();

        return Jwts.builder()
                .header().add(header).and()
                .claims(claims)
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(UUID uuid) {
        Date now = new Date();
        Header header = Jwts.header()
                .type("JWT")
                .build();
        Claims claims = Jwts.claims()
                .subject(uuid.toString())
                .add("type", TokenType.REFRESH_TOKEN)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + jwtProperties.getRefreshExpirationTime()))
                .build();

        String token = Jwts.builder()
                .header().add(header).and()
                .claims(claims)
                .signWith(key)
                .compact();

        tokenRepository.save(new Token(uuid, token));

        return token;
    }

    public String getEmail(String token) {
        return extractClaims(token).get("email", String.class);
    }

    public String getUsername(String token) {
        return extractClaims(token).get("name", String.class);
    }

    public UUID getUuid(String token) {
        return UUID.fromString(extractClaims(token).getSubject());
    }

    private Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new EmptyTokenException();
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }
}
