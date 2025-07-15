package com.harucourt.domain.user.exception;

import com.harucourt.domain.user.exception.error.UserErrorProperty;
import com.harucourt.shared.error.HaruException;

public class UserNotFoundException extends HaruException {
    public UserNotFoundException() {
        super(UserErrorProperty.USER_NOT_FOUND);
    }
}
