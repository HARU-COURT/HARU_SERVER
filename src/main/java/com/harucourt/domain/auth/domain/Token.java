package com.harucourt.domain.auth.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "token", timeToLive = 60 * 60 * 24 * 30)
public class Token {

    @Id
    private UUID uuid;

    private String token;

    public Token(UUID uuid, String token) {
        this.uuid = uuid;
        this.token = token;
    }
}
