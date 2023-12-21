package com.fcprovin.goal.check.domain.assist;

import com.fcprovin.goal.check.domain.goal.Goal;
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
class AssistRepositoryTest {

	@Autowired
	EntityManager em;

	@Autowired
	AssistRepository assistRepository;

	@Test
	void save() {
		Member goalMember = new Member("득점자");
		Member assistMember = new Member("도움자");
		em.persist(goalMember);
		em.persist(assistMember);

		Match match = new Match("상대", LocalDateTime.now());
		em.persist(match);

		Goal goal = new Goal(match, goalMember);
		em.persist(goal);

		Assist assist = new Assist(assistMember, goal);

		Assist saveAssist = assistRepository.save(assist);
		Assist findAssist = assistRepository.findById(saveAssist.getId()).get();

		assertThat(findAssist.getId()).isEqualTo(assist.getId());
		assertThat(findAssist.getMember()).isEqualTo(assistMember);
		assertThat(findAssist.getGoal()).isEqualTo(goal);
	}

}