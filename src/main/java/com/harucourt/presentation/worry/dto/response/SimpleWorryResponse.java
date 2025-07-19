package com.harucourt.presentation.worry.dto.response;

import com.harucourt.domain.worry.domain.Worry;
import com.harucourt.domain.worry.domain.type.JudgeType;
import com.harucourt.domain.worry.domain.type.WorryCategory;

import java.time.LocalDateTime;
import java.util.List;

public record SimpleWorryResponse(
        Long id,
        List<WorryCategory> categoryList,
        String title,
        JudgeType judgeType,
        LocalDateTime createdAt
) {
    public SimpleWorryResponse(Worry worry) {
        this(
                worry.getId(),
                worry.getCategoryList(),
                worry.getTitle(),
                worry.getJudge().getJudgeType(),
                worry.getCreatedAt()
        );
    }
}
