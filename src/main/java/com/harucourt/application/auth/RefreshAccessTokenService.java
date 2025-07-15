package com.harucourt.application.auth;

import com.harucourt.domain.auth.domain.Token;
import com.harucourt.domain.auth.domain.type.TokenType;
import com.harucourt.domain.auth.exception.ExpiredTokenException;
import com.harucourt.domain.auth.exception.InvalidTokenException;
import com.harucourt.domain.user.domain.User;
import com.harucourt.domain.user.exception.UserNotFoundException;
import com.harucourt.infrastructure.auth.JwtProvider;
import com.harucourt.infrastructure.persistence.auth.TokenRepository;
import com.harucourt.infrastructure.persistence.user.UserRepository;
import com.harucourt.presentation.auth.dto.request.RefreshTokenRequest;
import com.harucourt.presentation.auth.dto.response.AccessTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RefreshAccessTokenService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final TokenRepository tokenRepository;

    public AccessTokenResponse execute(RefreshTokenRequest request) {
        Token token = resolveToken(request.refreshToken());
        UUID uuid = token.getUuid();

        User user = userRepository.findByUuid(uuid)
                .orElseThrow(UserNotFoundException::new);

        return new AccessTokenResponse(jwtProvider.generateAccessToken(uuid, user.getEmail(), user.getUsername()));
    }

    private Token resolveToken(String refreshToken) {
        if (jwtProvider.getType(refreshToken) != TokenType.REFRESH_TOKEN)
            throw new InvalidTokenException();

        UUID uuid = jwtProvider.getUuid(refreshToken);
        Token token = tokenRepository.findById(uuid)
                .orElseThrow(ExpiredTokenException::new);

        if (!Objects.equals(refreshToken, token.getToken())) {
            throw new ExpiredTokenException();
        }

        return token;
    }
}
