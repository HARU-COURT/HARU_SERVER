package com.harucourt.presentation.auth;

import com.harucourt.application.auth.GoogleAuthService;
import com.harucourt.domain.user.domain.User;
import com.harucourt.infrastructure.auth.JwtProvider;
import com.harucourt.infrastructure.persistence.user.UserRepository;
import com.harucourt.presentation.auth.dto.request.LogInGoogleRequest;
import com.harucourt.presentation.auth.dto.request.TestRequest;
import com.harucourt.presentation.auth.dto.response.TokenResponse;
import com.harucourt.shared.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final GoogleAuthService googleAuthService;

    @PostMapping
    public ResponseEntity<CommonResponse<TokenResponse>> test(@RequestBody @Validated TestRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(RuntimeException::new);

        TokenResponse response = new TokenResponse(
                jwtProvider.generateAccessToken(user.getUuid(), user.getEmail(), user.getUsername()),
                jwtProvider.generateRefreshToken(user.getUuid())
        );

        return ResponseEntity.ok(CommonResponse.ok(response));
    }

    @PostMapping("/google")
    public ResponseEntity<CommonResponse<TokenResponse>> googleAuth(@RequestBody @Validated LogInGoogleRequest request) throws GeneralSecurityException, IOException {
        TokenResponse response = googleAuthService.execute(request);

        return ResponseEntity.ok(CommonResponse.ok(response));
    }
}
