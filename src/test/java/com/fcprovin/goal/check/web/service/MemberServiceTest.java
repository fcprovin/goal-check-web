package com.fcprovin.goal.check.web.service;

import com.fcprovin.goal.check.domain.assist.Assist;
import com.fcprovin.goal.check.domain.goal.Goal;
import com.fcprovin.goal.check.domain.match.Match;
import com.fcprovin.goal.check.domain.match.MatchRepository;
import com.fcprovin.goal.check.domain.member.Member;
import com.fcprovin.goal.check.domain.member.MemberRepository;
import com.fcprovin.goal.check.web.dto.response.AssistResponse;
import com.fcprovin.goal.check.web.dto.response.GoalResponse;
import com.fcprovin.goal.check.web.dto.response.MemberResponse;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceTest {

	@Autowired
	EntityManager em;

	@Autowired
	MemberService memberService;

	private Member member1, member2;
	private Match match1, match2;

	@BeforeEach
	void before() {
		member1 = new Member("홍길동");
		em.persist(member1);

		member2 = new Member("홍길동2");
		em.persist(member2);

		match1 = new Match("상대팀1", LocalDateTime.now());
		em.persist(match1);

		match2 = new Match("상대팀2", LocalDateTime.now());
		em.persist(match2);

		Goal goal1 = new Goal(match1, member1);
		Goal goal2 = new Goal(match2, member1);
		Goal goal3 = new Goal(match1, member2);
		em.persist(goal1);
		em.persist(goal2);
		em.persist(goal3);

		Assist assist1 = new Assist(member2, goal1);
		em.persist(assist1);
	}

	@Test
	void list() {
		List<MemberResponse> list = memberService.list();

		assertThat(list).extracting("name", String.class).contains("홍길동", "홍길동2");
		assertThat(list).extracting("goalCount", Long.class).contains(2L, 1L);
	}

	@Test
	void goals() {
		List<GoalResponse> goals = memberService.goals(member1.getId());

		assertThat(goals).hasSize(2);
	}

	@Test
	void assists() {
		List<AssistResponse> assists = memberService.assists(member2.getId());

		assertThat(assists).hasSize(1);
	}
}