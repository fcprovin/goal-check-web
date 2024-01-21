package com.fcprovin.goal.check.web.dto.response;

import com.fcprovin.goal.check.domain.goal.Goal;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class GoalResponse {

	private Long id;
	private String otherTeamName;
    private LocalDateTime matchDate;
	private String memberName;
	private String assistMemberName;

	@Builder
	public GoalResponse(Long id, String otherTeamName, LocalDateTime matchDate, String memberName, String assistMemberName) {
		this.id = id;
		this.otherTeamName = otherTeamName;
		this.matchDate = matchDate;
		this.memberName = memberName;
		this.assistMemberName = assistMemberName;
	}

	public static GoalResponse of(Goal goal) {
		return GoalResponse.builder()
				.id(goal.getId())
				.otherTeamName(goal.getMatch().getOtherTeamName())
				.matchDate(goal.getMatch().getDate())
				.memberName(goal.getMember().getName())
				.assistMemberName(Objects.nonNull(goal.getAssist()) ? goal.getAssist().getMember().getName() : "")
				.build();
	}
}
