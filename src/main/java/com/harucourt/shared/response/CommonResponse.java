package com.harucourt.shared.response;

import com.harucourt.shared.error.ErrorProperty;

public record CommonResponse<T>(
        String code,
        String message,
        T data
) {
    public static <T> CommonResponse<T> ok(T data) {
        return new CommonResponse<>(
                "OK",
                "ok",
                data
        );
    }

    public static <T> CommonResponse<T> fail(ErrorProperty errorProperty, T data) {
        return new CommonResponse<>(
                errorProperty.name(),
                errorProperty.getMessage(),
                data
        );
    }

    public static <T> CommonResponse<T> fail(ErrorProperty errorProperty) {
        return new CommonResponse<>(
                errorProperty.name(),
                errorProperty.getMessage(),
                null
        );
    }
}
