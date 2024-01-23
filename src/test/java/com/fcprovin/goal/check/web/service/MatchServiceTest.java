package com.fcprovin.goal.check.web.service;

import com.fcprovin.goal.check.domain.goal.Goal;
import com.fcprovin.goal.check.domain.match.Match;
import com.fcprovin.goal.check.domain.match.MatchRepository;
import com.fcprovin.goal.check.domain.member.Member;
import com.fcprovin.goal.check.domain.member.MemberRepository;
import com.fcprovin.goal.check.web.dto.form.GoalAddForm;
import com.fcprovin.goal.check.web.dto.form.MatchAddForm;
import com.fcprovin.goal.check.web.dto.form.MatchModifyForm;
import com.fcprovin.goal.check.web.dto.response.MatchDetailResponse;
import com.fcprovin.goal.check.web.dto.response.MatchResponse;
import com.fcprovin.goal.check.web.dto.response.MemberResponse;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class MatchServiceTest {

	@Autowired
	EntityManager em;

	@Autowired
	MatchService matchService;

	@Autowired
	MatchRepository matchRepository;

	@Autowired
	MemberRepository memberRepository;

	private Member member1, member2;
	private Match match1;

	@BeforeEach
	void before() {
		member1 = new Member("홍길동");
		em.persist(member1);

		member2 = new Member("홍길동2");
		em.persist(member2);

		match1 = new Match("상대팀1", LocalDateTime.now());
		em.persist(match1);

		em.persist(new Match("상대팀2", LocalDateTime.now()));
		em.persist(new Match("상대팀3", LocalDateTime.now()));
		em.persist(new Match("상대팀4", LocalDateTime.now()));
		em.persist(new Match("상대팀5", LocalDateTime.now()));
		em.persist(new Match("상대팀6", LocalDateTime.now()));
		em.persist(new Match("상대팀7", LocalDateTime.now()));
		em.persist(new Match("상대팀8", LocalDateTime.now()));
		em.persist(new Match("상대팀9", LocalDateTime.now()));
		em.persist(new Match("상대팀10", LocalDateTime.now()));
	}

	@Test
	void list() {
		List<MatchResponse> list = matchService.list(LocalDate.now());

		assertThat(list).hasSize(10);
	}

	@Test
	void detail() {
		Match match = matchRepository.findAll().stream().findAny().get();

		MatchDetailResponse detail = matchService.detail(match.getId());

		assertThat(detail.getId()).isEqualTo(match.getId());
		assertThat(detail.getOtherTeamName()).isEqualTo(match.getOtherTeamName());
		assertThat(detail.getDate()).isEqualTo(match.getDate());
	}

	@Test
	void add() {
		MatchAddForm form = new MatchAddForm();

		form.setDate(LocalDateTime.now());
		form.setOtherTeamName("프로빈");

		Match match = matchService.add(form);

		assertThat(match.getOtherTeamName()).isEqualTo(form.getOtherTeamName());
		assertThat(match.getDate()).isEqualTo(form.getDate());
	}

	@Test
	void modify() {
		Match match = matchRepository.findAll().stream().findAny().get();

		matchService.modify(match.getId(), MatchModifyForm.builder().lose(3).build());
		em.flush();

		MatchDetailResponse response = matchService.detail(match.getId());

		assertThat(response.getId()).isEqualTo(match.getId());
		assertThat(response.getLose()).isEqualTo(3);
	}

	@Test
	void remove() {
		Match match = matchRepository.findAll().stream().findAny().get();

		matchService.remove(match.getId());

		assertThatThrownBy(() -> matchRepository.findById(match.getId()).orElseThrow());
	}

	@Test
	void memberList() {
		List<MemberResponse> list = matchService.memberList();

		assertThat(list).extracting("name", String.class).contains("홍길동");
	}

	@Test
	void goalAdd() {
		Match match = matchService.goalAdd(match1.getId(), new GoalAddForm(member1.getId(), member2.getId()));

		MatchDetailResponse detail = matchService.detail(match.getId());

		assertThat(detail.getGoals()).hasSize(1);
	}

	@Test
	void goalRemove() {
		Goal goal = new Goal(match1, member1);
		em.persist(goal);

		Match match = matchService.goalRemove(goal.getId());

		assertThat(match.getId()).isEqualTo(match1.getId());
		assertThat(match.getGoals()).hasSize(0);
	}
}