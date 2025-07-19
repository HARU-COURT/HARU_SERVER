package com.harucourt.application.worry;

import com.harucourt.domain.user.domain.User;
import com.harucourt.domain.user.exception.UserNotFoundException;
import com.harucourt.domain.worry.domain.type.WorryCategory;
import com.harucourt.infrastructure.persistence.user.UserRepository;
import com.harucourt.infrastructure.persistence.worry.WorryRepository;
import com.harucourt.presentation.worry.dto.response.SimpleWorryResponse;
import com.harucourt.shared.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetMyWorriesService {

    private final UserRepository userRepository;
    private final WorryRepository worryRepository;

    public List<SimpleWorryResponse> execute(CustomUserDetails userDetails, WorryCategory category) {
        User user = userRepository.findByUuid(userDetails.uuid())
                .orElseThrow(UserNotFoundException::new);

        return worryRepository.findByUserAndCategory(user, category)
                .stream()
                .map(SimpleWorryResponse::new)
                .toList();
    }
}
