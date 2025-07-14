package com.harucourt.presentation.auth;

import com.harucourt.application.auth.GoogleAuthService;
import com.harucourt.application.auth.LogOutService;
import com.harucourt.application.auth.RefreshAccessTokenService;
import com.harucourt.presentation.auth.dto.request.LogInGoogleRequest;
import com.harucourt.presentation.auth.dto.request.RefreshTokenRequest;
import com.harucourt.presentation.auth.dto.response.AccessTokenResponse;
import com.harucourt.presentation.auth.dto.response.TokenResponse;
import com.harucourt.shared.auth.CustomUserDetails;
import com.harucourt.shared.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final GoogleAuthService googleAuthService;
    private final RefreshAccessTokenService refreshAccessTokenService;
    private final LogOutService logOutService;

    @PostMapping("/google")
    public ResponseEntity<CommonResponse<TokenResponse>> googleAuth(@RequestBody @Validated LogInGoogleRequest request) throws GeneralSecurityException, IOException {
        TokenResponse response = googleAuthService.execute(request);

        return ResponseEntity.ok(CommonResponse.ok(response));
    }

    @PatchMapping
    public ResponseEntity<CommonResponse<AccessTokenResponse>> refreshAccessToken(@RequestBody @Validated RefreshTokenRequest request) {
        AccessTokenResponse response = refreshAccessTokenService.execute(request);

        return ResponseEntity.ok(CommonResponse.ok(response));
    }

    @DeleteMapping
    public ResponseEntity<Void> logout(@AuthenticationPrincipal CustomUserDetails userDetails) {
        logOutService.execute(userDetails);

        return ResponseEntity.noContent().build();
    }
}
