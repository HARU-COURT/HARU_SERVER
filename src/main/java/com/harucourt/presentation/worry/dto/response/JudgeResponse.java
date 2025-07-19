package com.harucourt.presentation.worry.dto.response;

import com.harucourt.domain.worry.domain.type.JudgeType;
import com.harucourt.domain.worry.domain.value.Judge;

public record JudgeResponse(
        JudgeType judgeType,
        String judge,
        String lawyer,
        String prosecutor
) {
    public JudgeResponse(Judge judge) {
        this(
                judge.getJudgeType(),
                judge.getJudge(),
                judge.getLawyer(),
                judge.getProsecutor()
        );
    }
}
