package com.harucourt.domain.worry.exception;

import com.harucourt.domain.worry.exception.error.WorryErrorProperty;
import com.harucourt.shared.error.HaruException;

public class WorryNotFoundException extends HaruException {
    public WorryNotFoundException() {
        super(WorryErrorProperty.WORRY_NOT_FOUND);
    }
}
