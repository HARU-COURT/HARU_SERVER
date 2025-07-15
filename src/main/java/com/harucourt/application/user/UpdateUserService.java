package com.harucourt.application.user;

import com.harucourt.application.auth.RefreshAccessTokenService;
import com.harucourt.domain.user.domain.User;
import com.harucourt.domain.user.exception.UserNotFoundException;
import com.harucourt.infrastructure.persistence.user.UserRepository;
import com.harucourt.presentation.auth.dto.request.RefreshTokenRequest;
import com.harucourt.presentation.auth.dto.response.AccessTokenResponse;
import com.harucourt.presentation.user.dto.request.UpdateUserRequest;
import com.harucourt.shared.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UpdateUserService {

    private final UserRepository userRepository;
    private final RefreshAccessTokenService refreshAccessTokenService;

    @Transactional
    public AccessTokenResponse execute(CustomUserDetails userDetails, UpdateUserRequest request) {
        User user = userRepository.findByUuid(userDetails.uuid())
                .orElseThrow(UserNotFoundException::new);

        user.update(request.email(), request.username());

        return refreshAccessTokenService.execute(new RefreshTokenRequest(request.refreshToken()));
    }
}
