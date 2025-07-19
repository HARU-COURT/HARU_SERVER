package com.harucourt.infrastructure.persistence.worry;

import com.harucourt.domain.user.domain.User;
import com.harucourt.domain.worry.domain.Worry;
import com.harucourt.domain.worry.domain.type.JudgeType;
import com.harucourt.domain.worry.domain.type.WorryCategory;

import java.util.List;
import java.util.Map;

public interface WorryRepositoryCustom {

    List<Worry> findByUserAndCategory(User user, WorryCategory category);
    Map<JudgeType, Long> findJudgeTypeAndCountGroupByJudgeType(User user);
}
