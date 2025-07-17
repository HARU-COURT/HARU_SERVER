package com.harucourt.domain.worry.domain.value;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.harucourt.domain.worry.domain.type.JudgeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class Judge {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @JsonPropertyDescription("유죄면 GUILTY, 무죄면 NOT_GUILTY")
    private JudgeType judgeType;

    @Column(nullable = false)
    @JsonPropertyDescription("두 관점을 종합해 최종적인 결론을 도출하고 이유를 설명)")
    private String judge;

    @Column(nullable = false)
    @JsonPropertyDescription("고민자의 입장을 옹호")
    private String lawyer;

    @Column(nullable = false)
    @JsonPropertyDescription("문제점을 비판적으로 제기")
    private String prosecutor;
}
