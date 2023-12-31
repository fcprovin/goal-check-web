package com.fcprovin.goal.check.domain.goal;

import java.util.List;
import java.util.Optional;

public interface GoalQueryRepository {

	Optional<Goal> findQueryById(Long id);

	Long findCountByMemberId(Long id);

	Long findCountByMatchId(Long id);

	List<Goal> findByMemberId(Long id);

	List<Goal> findByMatchId(Long id);
}
