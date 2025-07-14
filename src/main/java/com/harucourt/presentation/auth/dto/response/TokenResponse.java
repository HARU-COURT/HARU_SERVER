package com.harucourt.presentation.auth.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
