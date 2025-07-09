package com.harucourt.shared.error;

import lombok.Getter;

@Getter
public class HaruException extends RuntimeException {

    private final ErrorProperty errorProperty;

    public HaruException(ErrorProperty errorProperty) {
        super(errorProperty.getMessage());
        this.errorProperty = errorProperty;
    }
}
