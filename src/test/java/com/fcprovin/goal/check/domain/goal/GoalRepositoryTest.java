package com.fcprovin.goal.check.domain.goal;

import com.fcprovin.goal.check.domain.match.Match;
import com.fcprovin.goal.check.domain.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class GoalRepositoryTest {

	@Autowired
	EntityManager em;

	@Autowired
	GoalRepository goalRepository;

	@Test
	void save() {
		Member member = new Member("테스터");
		em.persist(member);

		Match match = new Match("상대", LocalDateTime.now());
		em.persist(match);

		Goal goal = new Goal(match, member);

		Goal saveGoal = goalRepository.save(goal);
		Goal findGoal = goalRepository.findById(saveGoal.getId()).get();

		assertThat(findGoal.getId()).isEqualTo(goal.getId());
		assertThat(findGoal.getMember()).isEqualTo(member);
		assertThat(findGoal.getMatch()).isEqualTo(match);
	}
}