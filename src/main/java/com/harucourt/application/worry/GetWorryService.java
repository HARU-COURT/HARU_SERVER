package com.harucourt.application.worry;

import com.harucourt.domain.auth.exception.error.AuthorityMismatchException;
import com.harucourt.domain.user.domain.User;
import com.harucourt.domain.user.exception.UserNotFoundException;
import com.harucourt.domain.worry.domain.Worry;
import com.harucourt.domain.worry.exception.WorryNotFoundException;
import com.harucourt.infrastructure.persistence.user.UserRepository;
import com.harucourt.infrastructure.persistence.worry.WorryRepository;
import com.harucourt.presentation.worry.dto.response.WorryResponse;
import com.harucourt.shared.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetWorryService {

    private final UserRepository userRepository;
    private final WorryRepository worryRepository;

    public WorryResponse execute(CustomUserDetails userDetails, Long id) {
        User user = userRepository.findByUuid(userDetails.uuid())
                .orElseThrow(UserNotFoundException::new);
        Worry worry = worryRepository.findById(id)
                .orElseThrow(WorryNotFoundException::new);

        validateUser(user, worry);

        return new WorryResponse(worry);
    }

    private void validateUser(User user, Worry worry) {
        if (!worry.getUser().equals(user)) {
            throw new AuthorityMismatchException();
        }
    }
}
