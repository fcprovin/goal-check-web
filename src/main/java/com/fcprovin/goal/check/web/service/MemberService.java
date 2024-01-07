package com.fcprovin.goal.check.web.service;

import com.fcprovin.goal.check.domain.assist.AssistRepository;
import com.fcprovin.goal.check.domain.goal.GoalRepository;
import com.fcprovin.goal.check.domain.member.MemberRepository;
import com.fcprovin.goal.check.web.dto.response.AssistResponse;
import com.fcprovin.goal.check.web.dto.response.GoalResponse;
import com.fcprovin.goal.check.web.dto.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final GoalRepository goalRepository;
	private final AssistRepository assistRepository;

	public List<MemberResponse> list() {
		return memberRepository.findAll().stream()
				.map(MemberResponse::of)
				.peek(this::setAttackPoint)
				.toList();
	}

	public List<GoalResponse> goals(Long id) {
		return goalRepository.findByMemberId(id).stream()
				.map(GoalResponse::of)
				.toList();
	}

	public List<AssistResponse> assists(Long id) {
		return assistRepository.findByMemberId(id).stream()
				.map(AssistResponse::of)
				.toList();
	}

	private void setAttackPoint(MemberResponse memberResponse) {
		memberResponse.setGoalCount(goalRepository.findCountByMemberId(memberResponse.getId()));
		memberResponse.setAssistCount(assistRepository.findCountByMemberId(memberResponse.getId()));
	}
}
