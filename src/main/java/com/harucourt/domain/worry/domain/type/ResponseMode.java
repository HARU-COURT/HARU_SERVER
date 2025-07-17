package com.harucourt.domain.worry.domain.type;

import com.harucourt.shared.enums.EnumProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMode implements EnumProperty {

    FACT_CHECK("팩트체크"),
    FUNNY("재미"),
    EMPATHY("공감");

    private final String description;
}
