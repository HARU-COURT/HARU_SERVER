package com.harucourt.presentation.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(

        @NotBlank(message = "필수값입니다.")
        String refreshToken
) {
}
