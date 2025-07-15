package com.harucourt.application.user;

import com.harucourt.application.auth.LogOutService;
import com.harucourt.domain.user.domain.User;
import com.harucourt.domain.user.exception.UserNotFoundException;
import com.harucourt.infrastructure.persistence.user.UserRepository;
import com.harucourt.shared.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteUserService {

    private final UserRepository userRepository;
    private final LogOutService logOutService;

    public void execute(CustomUserDetails userDetails) {
        User user = userRepository.findByUuid(userDetails.uuid())
                .orElseThrow(UserNotFoundException::new);

        logOutService.execute(userDetails);
        userRepository.delete(user);
    }
}
