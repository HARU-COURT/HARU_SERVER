package com.harucourt.presentation.user.dto.response;

public record UserInfoResponse(
        String email,
        String username,
        Long guiltyCount,
        Long notGuiltyCount
) {
}
