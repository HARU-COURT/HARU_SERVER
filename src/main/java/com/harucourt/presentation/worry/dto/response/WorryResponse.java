package com.harucourt.presentation.worry.dto.response;

import com.harucourt.domain.worry.domain.Worry;
import com.harucourt.domain.worry.domain.type.ResponseMode;
import com.harucourt.domain.worry.domain.type.WorryCategory;

import java.util.List;

public record WorryResponse(
        Long id,
        List<WorryCategory> categoryList,
        String title,
        String content,
        ResponseMode responseMode,
        JudgeResponse judge
) {
    public WorryResponse(Worry worry) {
        this(
                worry.getId(),
                worry.getCategoryList(),
                worry.getTitle(),
                worry.getContent(),
                worry.getResponseMode(),
                new JudgeResponse(worry.getJudge())
        );
    }
}
