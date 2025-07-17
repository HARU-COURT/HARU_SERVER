package com.harucourt.presentation.worry.dto.request;

import com.harucourt.domain.worry.domain.type.ResponseMode;
import com.harucourt.domain.worry.domain.type.WorryCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateWorryRequest(

        @NotNull(message = "필수값입니다.")
        @Size(min = 1, message = "최소 1개 이상 선택해야합니다.")
        List<WorryCategory> categoryList,

        @NotBlank(message = "필수값입니다.")
        @Size(max = 50, message = "50자 이하여야 합니다.")
        String title,

        @NotBlank(message = "필수값입니다.")
        @Size(max = 1000, message = "1000자 이하여야 합니다.")
        String content,

        @NotNull(message = "필수값입니다.")
        ResponseMode responseMode
) {
}
