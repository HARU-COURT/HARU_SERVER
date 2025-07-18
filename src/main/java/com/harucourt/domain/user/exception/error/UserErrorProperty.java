package com.harucourt.domain.user.exception.error;

import com.harucourt.shared.error.ErrorProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorProperty implements ErrorProperty {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.")
    ;

    private final HttpStatus status;
    private final String message;
}
