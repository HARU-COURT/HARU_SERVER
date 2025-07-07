package com.harucourt.domain.auth.domain;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "token", timeToLive = 60 * 60 * 24 * 30)
public class Token {

    @Id
    private String email;

    private String token;

    public Token(String email, String token) {
        this.email = email;
        this.token = token;
    }
}
