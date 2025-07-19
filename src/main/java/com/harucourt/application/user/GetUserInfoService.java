package com.harucourt.application.user;

import com.harucourt.domain.user.domain.User;
import com.harucourt.domain.worry.domain.type.JudgeType;
import com.harucourt.infrastructure.persistence.user.UserRepository;
import com.harucourt.infrastructure.persistence.worry.WorryRepository;
import com.harucourt.presentation.user.dto.response.UserInfoResponse;
import com.harucourt.shared.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class GetUserInfoService {

    private final UserRepository userRepository;
    private final WorryRepository worryRepository;

    public UserInfoResponse execute(CustomUserDetails userDetails) {
        User user = userRepository.findByUuid(userDetails.uuid())
                .orElseThrow();

        Map<JudgeType, Long> result = worryRepository.findJudgeTypeAndCountGroupByJudgeType(user);

        return new UserInfoResponse(
                user.getEmail(),
                user.getUsername(),
                result.get(JudgeType.GUILTY),
                result.get(JudgeType.NOT_GUILTY)
        );
    }
}
