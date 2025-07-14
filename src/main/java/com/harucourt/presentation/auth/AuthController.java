package com.harucourt.presentation.auth;

import com.harucourt.application.auth.GoogleAuthService;
import com.harucourt.presentation.auth.dto.request.LogInGoogleRequest;
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

    private final GoogleAuthService googleAuthService;

    @PostMapping("/google")
    public ResponseEntity<CommonResponse<TokenResponse>> googleAuth(@RequestBody @Validated LogInGoogleRequest request) throws GeneralSecurityException, IOException {
        TokenResponse response = googleAuthService.execute(request);

        return ResponseEntity.ok(CommonResponse.ok(response));
    }
}
