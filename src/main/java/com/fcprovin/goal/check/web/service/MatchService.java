package com.fcprovin.goal.check.web.service;

import com.fcprovin.goal.check.domain.assist.Assist;
import com.fcprovin.goal.check.domain.assist.AssistRepository;
import com.fcprovin.goal.check.domain.goal.Goal;
import com.fcprovin.goal.check.domain.goal.GoalRepository;
import com.fcprovin.goal.check.domain.match.Match;
import com.fcprovin.goal.check.domain.match.MatchRepository;
import com.fcprovin.goal.check.domain.member.MemberRepository;
import com.fcprovin.goal.check.web.dto.form.GoalAddForm;
import com.fcprovin.goal.check.web.dto.form.MatchAddForm;
import com.fcprovin.goal.check.web.dto.form.MatchModifyForm;
import com.fcprovin.goal.check.web.dto.response.GoalResponse;
import com.fcprovin.goal.check.web.dto.response.MatchDetailResponse;
import com.fcprovin.goal.check.web.dto.response.MatchResponse;
import com.fcprovin.goal.check.web.dto.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.time.LocalTime.MAX;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchService {

	private final GoalRepository goalRepository;
	private final MatchRepository matchRepository;
	private final MemberRepository memberRepository;
	private final AssistRepository assistRepository;

	@Transactional(readOnly = true)
	public List<MatchResponse> list(LocalDate year) {
		return matchRepository
				.findAllByDateBetweenOrderByCreatedDateDesc(year.with(firstDayOfYear()).atStartOfDay(), year.with(lastDayOfYear()).atTime(MAX))
				.stream()
				.map(MatchResponse::of)
				.peek(this::setGoalCount)
				.toList();
	}

	@Transactional(readOnly = true)
	public MatchDetailResponse detail(Long id) {
		Match match = matchRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Match not found"));

		MatchDetailResponse response = MatchDetailResponse.of(match);
		response.setGoals(goalRepository.findByMatchId(id).stream()
				.map(GoalResponse::of)
				.peek(this::setAssistMemberName)
				.toList());

		return response;
	}

	public Match add(MatchAddForm form) {
		return matchRepository.save(form.toEntity());
	}

	public void modify(Long id, MatchModifyForm form) {
		Match match = matchRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Match not found"));

		if (!match.getOtherTeamName().equals(form.getOtherTeamName())) {
			match.setOtherTeamName(form.getOtherTeamName());
		}

		if (!match.getDate().equals(form.getDate())) {
			match.setDate(form.getDate());
		}

		if (!match.getLose().equals(form.getLose())) {
			match.setLose(form.getLose());
		}
	}

	public void remove(Long id) {
		goalRepository.findByMatchId(id)
						.forEach(g -> this.goalRemove(g.getId()));

		matchRepository.deleteById(id);
	}

	public List<MemberResponse> memberList() {
		return memberRepository.findAll().stream()
				.map(MemberResponse::of)
				.toList();
	}

	public Match goalAdd(Long matchId, GoalAddForm form) {
		if (isNull(matchId)) {
			throw new IllegalArgumentException("Match Id is required");
		}

		form.setMatchId(matchId);

		Match match = matchRepository.findById(form.getMatchId())
				.orElseThrow(() -> new IllegalArgumentException("Match not found"));

		Goal goal = goalRepository.save(form.ofGoalEntity(match, memberRepository.findById(form.getGoalMemberId())
				.orElseThrow(() -> new IllegalArgumentException("Goal member not found"))));

		if (nonNull(form.getAssistMemberId())) {
			assistRepository.save(form.ofAssistEntity(memberRepository.findById(form.getAssistMemberId())
				.orElseThrow(() -> new IllegalArgumentException("Assist member not found")), goal));
		}

		return match;
	}

	public Match goalRemove(Long id) {
		Goal goal = goalRepository.findQueryById(id)
				.orElseThrow(() -> new IllegalArgumentException("Goal member not found"));

		assistRepository.findByGoalId(id)
				.ifPresent(a -> assistRepository.deleteById(a.getId()));

		goalRepository.delete(goal);

		return goal.getMatch();
	}

	private void setGoalCount(MatchResponse response) {
		response.setGoalCount(goalRepository.findCountByMatchId(response.getId()));
	}

	private void setAssistMemberName(GoalResponse response) {
		response.setAssistMemberName(getAssistMemberName(response));
	}

	private String getAssistMemberName(GoalResponse response) {
		Optional<Assist> assist = assistRepository.findByGoalId(response.getId());

		return assist.isPresent() ? assist.get().getMember().getName() : "";
	}
}
