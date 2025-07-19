package com.harucourt.infrastructure.persistence.worry;

import com.harucourt.domain.user.domain.User;
import com.harucourt.domain.worry.domain.Worry;
import com.harucourt.domain.worry.domain.type.JudgeType;
import com.harucourt.domain.worry.domain.type.WorryCategory;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static com.harucourt.domain.worry.domain.QWorry.worry;

@RequiredArgsConstructor
@Repository
public class WorryRepositoryImpl implements WorryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Worry> findByUserAndCategory(User user, WorryCategory category) {
        return queryFactory
                .selectFrom(worry)
                .where(worry.user.eq(user).and(
                        category != null ? worry.categoryList.contains(category) : null
                ))
                .fetch();
    }

    @Override
    public Map<JudgeType, Long> findJudgeTypeAndCountGroupByJudgeType(User user) {
        List<Tuple> result = queryFactory
                .select(worry.judge.judgeType, worry.judge.count())
                .from(worry)
                .where(worry.user.eq(user))
                .groupBy(worry.judge.judgeType)
                .fetch();

        EnumMap<JudgeType, Long> countMap = new EnumMap<>(JudgeType.class);

        result.forEach(tuple -> {
            JudgeType judgeType = tuple.get(worry.judge.judgeType);
            Long count = tuple.get(worry.judge.count());
            countMap.put(judgeType, count);
        });

        for (JudgeType judgeType : JudgeType.values()) {
            countMap.putIfAbsent(judgeType, 0L);
        }

        return countMap;
    }
}
