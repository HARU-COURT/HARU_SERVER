package com.harucourt.domain.worry.domain.type;

import com.harucourt.shared.enums.EnumProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorryCategory implements EnumProperty {
    FRIEND("친구"),
    FAMILY("가족"),
    SCHOOL("학교"),
    CAREER("진로"),
    ROMANCE("연애"),
    OTHERS("기타"),
    ;

    private final String description;
}
