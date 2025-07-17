package com.harucourt.domain.worry.domain.type;

import com.harucourt.shared.enums.EnumProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JudgeType implements EnumProperty {
    GUILTY("유죄"),
    NOT_GUILTY("무죄"),
    ;

    private final String description;
}
