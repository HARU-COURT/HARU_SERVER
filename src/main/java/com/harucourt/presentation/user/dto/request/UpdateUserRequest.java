package com.harucourt.presentation.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(

        @NotBlank(message = "필수값입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,

        @NotBlank(message = "필수값입니다.")
        String username,

        @NotBlank(message = "필수값입니다.")
        String refreshToken
) {
}
