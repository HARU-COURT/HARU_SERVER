package com.harucourt.domain.auth.exception;

import com.harucourt.domain.auth.exception.error.AuthErrorProperty;
import com.harucourt.shared.error.HaruException;

public class InvalidTokenException extends HaruException {
    public InvalidTokenException() {
        super(AuthErrorProperty.INVALID_TOKEN);
    }
}
