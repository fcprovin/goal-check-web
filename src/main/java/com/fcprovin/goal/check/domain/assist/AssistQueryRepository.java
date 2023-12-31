package com.fcprovin.goal.check.domain.assist;

import java.util.List;
import java.util.Optional;

public interface AssistQueryRepository {

	Long findCountByMemberId(Long id);

	List<Assist> findByMemberId(Long id);

	Optional<Assist> findByGoalId(Long id);
}
