package com.harucourt.infrastructure.auth;

import com.harucourt.domain.auth.domain.Token;
import com.harucourt.domain.auth.domain.type.TokenType;
import com.harucourt.infrastructure.persistence.auth.TokenRepository;
import com.harucourt.shared.config.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

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

    public String generateAccessToken(String uuid) {
        return generateToken(uuid, TokenType.ACCESS_TOKEN, jwtProperties.getAccessExpirationTime());
    }

    public String generateRefreshToken(String uuid) {
        String token = generateToken(uuid, TokenType.REFRESH_TOKEN, jwtProperties.getRefreshExpirationTime());
        tokenRepository.save(new Token(uuid, token));

        return token;
    }

    private String generateToken(String uuid, TokenType type, Long time) {
        Claims claims = Jwts.claims().build();
        claims.put("type", type.name());
        Date now = new Date();

        return Jwts.builder()
                .claims(claims)
                .subject(uuid)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + time))
                .signWith(key)
                .compact();
    }
}
