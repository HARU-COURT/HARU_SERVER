package com.harucourt.application.auth;

import com.harucourt.infrastructure.persistence.auth.TokenRepository;
import com.harucourt.shared.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogOutService {

    private final TokenRepository tokenRepository;

    public void execute(CustomUserDetails userDetails) {
        tokenRepository.deleteById(userDetails.uuid());
    }
}
