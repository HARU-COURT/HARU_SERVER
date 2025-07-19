package com.harucourt.presentation.worry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.harucourt.application.worry.CreateWorryService;
import com.harucourt.application.worry.GetMyWorriesService;
import com.harucourt.application.worry.GetWorryService;
import com.harucourt.domain.worry.domain.type.WorryCategory;
import com.harucourt.presentation.worry.dto.request.CreateWorryRequest;
import com.harucourt.presentation.worry.dto.response.SimpleWorryResponse;
import com.harucourt.presentation.worry.dto.response.WorryResponse;
import com.harucourt.shared.auth.CustomUserDetails;
import com.harucourt.shared.response.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/worries")
@RestController
public class WorryController {

    private final CreateWorryService createWorryService;
    private final GetMyWorriesService getMyWorriesService;
    private final GetWorryService getWorryService;

    @PostMapping
    public ResponseEntity<Void> createWorry(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid CreateWorryRequest request
    ) throws JsonProcessingException {
        Long id = createWorryService.execute(userDetails, request);

        return ResponseEntity
                .created(URI.create("/worries/" + id))
                .build();
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<SimpleWorryResponse>>> getMyWorries(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(required = false) WorryCategory category
    ) {
        List<SimpleWorryResponse> responseList = getMyWorriesService.execute(userDetails, category);

        return ResponseEntity.ok(CommonResponse.ok(responseList));
    }

    @GetMapping("/{worry-id}")
    public ResponseEntity<CommonResponse<WorryResponse>> getWorry(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable(name = "worry-id") Long worryId
    ) {
        WorryResponse response = getWorryService.execute(userDetails, worryId);

        return ResponseEntity.ok(CommonResponse.ok(response));
    }
}
