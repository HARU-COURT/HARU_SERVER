package com.harucourt.presentation.user;

import com.harucourt.application.user.DeleteUserService;
import com.harucourt.application.user.GetUserInfoService;
import com.harucourt.application.user.UpdateUserService;
import com.harucourt.presentation.auth.dto.response.AccessTokenResponse;
import com.harucourt.presentation.user.dto.request.UpdateUserRequest;
import com.harucourt.presentation.user.dto.response.UserInfoResponse;
import com.harucourt.shared.auth.CustomUserDetails;
import com.harucourt.shared.response.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UpdateUserService updateUserService;
    private final DeleteUserService deleteUserService;
    private final GetUserInfoService getUserInfoService;

    @PutMapping
    public ResponseEntity<CommonResponse<AccessTokenResponse>> updateUser(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid UpdateUserRequest request
    ) {
        AccessTokenResponse response = updateUserService.execute(userDetails, request);

        return ResponseEntity.ok(CommonResponse.ok(response));
    }

    @GetMapping("/me")
    public ResponseEntity<CommonResponse<UserInfoResponse>> getUserInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        UserInfoResponse response = getUserInfoService.execute(userDetails);

        return ResponseEntity.ok(CommonResponse.ok(response));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        deleteUserService.execute(userDetails);

        return ResponseEntity.noContent().build();
    }
}
