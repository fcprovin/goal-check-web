package com.fcprovin.goal.check.web.service;

import com.fcprovin.goal.check.config.security.CustomUserDetails;
import com.fcprovin.goal.check.domain.assist.AssistRepository;
import com.fcprovin.goal.check.domain.attend.Attend;
import com.fcprovin.goal.check.domain.attend.AttendRepository;
import com.fcprovin.goal.check.domain.goal.GoalRepository;
import com.fcprovin.goal.check.domain.match.Match;
import com.fcprovin.goal.check.domain.match.MatchRepository;
import com.fcprovin.goal.check.domain.member.Member;
import com.fcprovin.goal.check.domain.member.MemberRepository;
import com.fcprovin.goal.check.notification.service.SlackWebhookService;
import com.fcprovin.goal.check.web.dto.form.AttendAddForm;
import com.fcprovin.goal.check.web.dto.form.MemberPasswordForm;
import com.fcprovin.goal.check.web.dto.response.MatchAttendResponse;
import com.fcprovin.goal.check.web.dto.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static java.time.LocalTime.MAX;
import static java.time.LocalTime.MIN;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MeService {

    private final AttendRepository attendRepository;
    private final MemberRepository memberRepository;
    private final MatchRepository matchRepository;
    private final GoalRepository goalRepository;
    private final AssistRepository assistRepository;

    private final PasswordEncoder passwordEncoder;
    private final SlackWebhookService webhookService;

    @Transactional(readOnly = true)
    public MemberResponse getMember(CustomUserDetails userDetails) {
        return getMemberResponse(findMember(userDetails));
    }

    @Transactional(readOnly = true)
	public List<MatchAttendResponse> getMatches(CustomUserDetails userDetails) {
		LocalDate now = LocalDate.now();

		return matchRepository
				.findAllByDateBetween(now.atTime(MIN), now.plusWeeks(1L).atTime(MAX))
				.stream()
				.map(m -> getMatchAttendResponse(m, userDetails.getId()))
				.toList();
	}

    public void password(CustomUserDetails userDetails, MemberPasswordForm form) {
        Member member = findMember(userDetails);
        member.setPassword(passwordEncoder.encode(form.getPassword()));
    }

    public void attend(CustomUserDetails userDetails, AttendAddForm form) {
        Member member = findMember(userDetails);
        Match match = findMatch(form.getId());

        Attend attend = attendRepository.save(form.toEntity(match, member));
        webhookService.execute(attend);
    }

    private Member findMember(CustomUserDetails userDetails) {
        return memberRepository.findById(userDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("Member not exist"));
    }

    private Match findMatch(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Match not exist"));
    }

    private MemberResponse getMemberResponse(Member member) {
        MemberResponse response = MemberResponse.of(member);

        response.setGoalCount(goalRepository.findCountByMemberId(member.getId()));
        response.setAssistCount(assistRepository.findCountByMemberId(member.getId()));

        return response;
    }

    private MatchAttendResponse getMatchAttendResponse(Match match, Long memberId) {
        MatchAttendResponse response = MatchAttendResponse.of(match);

        attendRepository.findByMatchIdAndMemberId(match.getId(), memberId).ifPresent(a -> {
            response.setAttend(true);
            response.setAttendDate(a.getCreatedDate());
        });

        return response;
    }
}
