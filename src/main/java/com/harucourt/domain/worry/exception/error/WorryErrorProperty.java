package com.harucourt.domain.worry.exception.error;

import com.harucourt.shared.error.ErrorProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum WorryErrorProperty implements ErrorProperty {
    WORRY_NOT_FOUND(HttpStatus.NOT_FOUND, "고민을 찾을 수 없습니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
