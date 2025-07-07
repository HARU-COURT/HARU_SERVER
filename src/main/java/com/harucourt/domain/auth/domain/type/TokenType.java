package com.harucourt.domain.auth.domain.type;

import com.harucourt.shared.enums.EnumProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenType implements EnumProperty {
    ACCESS_TOKEN("액세스 토큰"),
    REFRESH_TOKEN("리프레시 토큰"),
    ;

    private final String description;
}
