package com.fcprovin.goal.check.domain.assist;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.fcprovin.goal.check.domain.assist.QAssist.assist;
import static com.fcprovin.goal.check.domain.goal.QGoal.goal;
import static com.fcprovin.goal.check.domain.match.QMatch.match;
import static com.fcprovin.goal.check.domain.member.QMember.member;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
public class AssistRepositoryImpl implements AssistQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Long findCountByMemberId(Long id) {
		return queryFactory
				.select(assist.count())
				.from(assist)
				.innerJoin(assist.member, member)
				.where(assist.member.id.eq(id))
				.fetchOne();
	}

	@Override
	public List<Assist> findByMemberId(Long id) {
		return queryFactory
				.selectFrom(assist)
				.join(assist.goal, goal).fetchJoin()
				.join(assist.goal.match, match).fetchJoin()
				.where(assist.member.id.eq(id))
				.orderBy(assist.createdDate.desc())
				.fetch();
	}

	@Override
	public Optional<Assist> findByGoalId(Long id) {
		return ofNullable(queryFactory
				.selectFrom(assist)
				.join(assist.goal, goal).fetchJoin()
				.where(assist.goal.id.eq(id))
				.fetchOne());
	}
}
