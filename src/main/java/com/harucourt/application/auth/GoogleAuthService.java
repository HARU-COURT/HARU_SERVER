package com.harucourt.application.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.harucourt.domain.auth.exception.ExpiredTokenException;
import com.harucourt.domain.user.domain.User;
import com.harucourt.infrastructure.auth.JwtProvider;
import com.harucourt.infrastructure.persistence.user.UserRepository;
import com.harucourt.presentation.auth.dto.request.LogInGoogleRequest;
import com.harucourt.presentation.auth.dto.response.TokenResponse;
import com.harucourt.shared.config.properties.GoogleOAuthProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class GoogleAuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final GoogleOAuthProperties googleOAuthProperties;

    public TokenResponse execute(LogInGoogleRequest request) throws GeneralSecurityException, IOException {
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(googleOAuthProperties.getClientId()))
                .build();

        GoogleIdToken idToken = verifier.verify(request.idToken());

        if (idToken != null) {
            Payload payload = idToken.getPayload();

            String email = payload.getEmail();
            String name = (String) payload.get("name");

            User user = userRepository.findByEmail(email)
                    .orElseGet(() -> userRepository.save(new User(email, name)));

            return new TokenResponse(
                    jwtProvider.generateAccessToken(user.getUuid(), email, name),
                    jwtProvider.generateRefreshToken(user.getUuid())
            );
        } else {
            throw new ExpiredTokenException();
        }
    }
}
