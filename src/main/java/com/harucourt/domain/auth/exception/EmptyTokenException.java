package com.harucourt.domain.auth.exception;

import com.harucourt.domain.auth.exception.error.AuthErrorProperty;
import com.harucourt.shared.error.HaruException;

public class EmptyTokenException extends HaruException {
    public EmptyTokenException() {
        super(AuthErrorProperty.EMPTY_TOKEN);
    }
}
