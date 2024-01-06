package com.fcprovin.goal.check.domain.goal;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.fcprovin.goal.check.domain.goal.QGoal.goal;
import static com.fcprovin.goal.check.domain.match.QMatch.match;
import static com.fcprovin.goal.check.domain.member.QMember.member;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
public class GoalRepositoryImpl implements GoalQueryRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<Goal> findQueryById(Long id) {
		return ofNullable(queryFactory
				.selectFrom(goal)
				.join(goal.match, match).fetchJoin()
				.fetchOne());
	}

	@Override
	public Long findCountByMemberId(Long id) {
		return queryFactory
				.select(goal.count())
				.from(goal)
				.innerJoin(goal.member, member)
				.where(goal.member.id.eq(id))
				.fetchOne();
	}

	@Override
	public Long findCountByMatchId(Long id) {
		return queryFactory
				.select(goal.count())
				.from(goal)
				.innerJoin(goal.match, match)
				.where(goal.match.id.eq(id))
				.fetchOne();
	}

	@Override
	public List<Goal> findByMemberId(Long id) {
		return queryFactory
				.selectFrom(goal)
				.join(goal.match, match).fetchJoin()
				.join(goal.member, member).fetchJoin()
				.where(goal.member.id.eq(id))
				.fetch();
	}

	@Override
	public List<Goal> findByMatchId(Long id) {
		return queryFactory
				.selectFrom(goal)
				.join(goal.match, match).fetchJoin()
				.join(goal.member, member).fetchJoin()
				.where(goal.match.id.eq(id))
				.fetch();
	}
}
