package com.harucourt.presentation.worry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.harucourt.application.worry.CreateWorryService;
import com.harucourt.presentation.worry.dto.request.CreateWorryRequest;
import com.harucourt.shared.auth.CustomUserDetails;
import com.harucourt.shared.response.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/worries")
@RestController
public class WorryController {

    private final CreateWorryService createWorryService;

    @PostMapping
    public ResponseEntity<CommonResponse<Void>> createWorry(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid CreateWorryRequest request
    ) throws JsonProcessingException {
        Long id = createWorryService.execute(userDetails, request);

        return ResponseEntity
                .created(URI.create("/worries/" + id))
                .build();
    }
}
