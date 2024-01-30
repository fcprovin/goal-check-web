package com.fcprovin.goal.check.domain.attend;

import java.util.List;
import java.util.Optional;

public interface AttendQueryRepository {

	List<Attend> findByMatchId(Long id);

	Optional<Attend> findByMatchIdAndMemberId(Long matchId, Long memberId);
}
