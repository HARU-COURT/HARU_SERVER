package com.harucourt.domain.auth.exception.error;

import com.harucourt.shared.error.ErrorProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorProperty implements ErrorProperty {

    EMPTY_TOKEN(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
