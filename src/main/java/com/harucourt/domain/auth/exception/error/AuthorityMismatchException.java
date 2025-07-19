package com.harucourt.domain.auth.exception.error;

import com.harucourt.shared.error.HaruException;

public class AuthorityMismatchException extends HaruException {
    public AuthorityMismatchException() {
        super(AuthErrorProperty.AUTHORITY_MISMATCH);
    }
}
