package com.fcprovin.goal.check.domain.match;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class MatchRepositoryTest {

	@Autowired
	EntityManager em;

	@Autowired
	MatchRepository matchRepository;

	@Test
	void save() {
		Match match = new Match("상대", LocalDateTime.now());

		Match saveMatch = matchRepository.save(match);
		Match findMatch = matchRepository.findById(saveMatch.getId()).get();

		assertThat(findMatch.getId()).isEqualTo(match.getId());
		assertThat(findMatch.getOtherTeamName()).isEqualTo(match.getOtherTeamName());
	}

	@Test
	void findByDate() {
		//given
		Match match = new Match("상대", LocalDateTime.of(2023, 12, 23, 12, 0));
		em.persist(match);

		//when
		List<Match> matches = matchRepository.findAllByDateBetween(LocalDate.of(2023, 12, 1).atStartOfDay(),
				LocalDate.of(2023, 12, 31).atTime(LocalTime.MAX));

		//then
		assertThat(matches).containsExactly(match);
	}
}