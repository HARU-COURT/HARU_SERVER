package com.harucourt.application.auth;

import com.harucourt.domain.user.domain.User;
import com.harucourt.domain.user.exception.UserNotFoundException;
import com.harucourt.infrastructure.persistence.auth.TokenRepository;
import com.harucourt.infrastructure.persistence.user.UserRepository;
import com.harucourt.shared.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogOutService {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    public void execute(CustomUserDetails userDetails) {
        User user = userRepository.findByUuid(userDetails.uuid())
                        .orElseThrow(UserNotFoundException::new);

        tokenRepository.deleteById(user.getUuid());
    }
}
