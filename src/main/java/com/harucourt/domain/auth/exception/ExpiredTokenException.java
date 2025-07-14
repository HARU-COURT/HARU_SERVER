package com.harucourt.domain.auth.exception;

import com.harucourt.domain.auth.exception.error.AuthErrorProperty;
import com.harucourt.shared.error.HaruException;

public class ExpiredTokenException extends HaruException {
    public ExpiredTokenException() {
        super(AuthErrorProperty.EXPIRED_TOKEN);
    }
}
