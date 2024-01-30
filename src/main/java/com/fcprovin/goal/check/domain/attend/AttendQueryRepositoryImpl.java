package com.fcprovin.goal.check.domain.attend;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.fcprovin.goal.check.domain.attend.QAttend.attend;
import static com.fcprovin.goal.check.domain.match.QMatch.match;
import static com.fcprovin.goal.check.domain.member.QMember.member;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
public class AttendQueryRepositoryImpl implements AttendQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Attend> findByMatchId(Long id) {
        return queryFactory
                .selectFrom(attend)
                .join(attend.match, match).fetchJoin()
                .join(attend.member, member).fetchJoin()
                .where(attend.match.id.eq(id))
                .orderBy(attend.createdDate.desc())
                .fetch();
    }

    @Override
    public Optional<Attend> findByMatchIdAndMemberId(Long matchId, Long memberId) {
        return ofNullable(queryFactory
				.selectFrom(attend)
                .join(attend.match, match).fetchJoin()
                .join(attend.member, member).fetchJoin()
                .where(attend.match.id.eq(matchId), attend.member.id.eq(memberId))
				.fetchOne());
    }
}
