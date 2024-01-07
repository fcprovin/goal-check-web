package com.fcprovin.goal.check.web.dto.response;

import com.fcprovin.goal.check.domain.assist.Assist;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssistResponse {

	private String otherTeamName;
    private LocalDateTime matchDate;
	private String goalMemberName;

	@Builder
	public AssistResponse(String otherTeamName, LocalDateTime matchDate, String goalMemberName) {
		this.otherTeamName = otherTeamName;
		this.matchDate = matchDate;
		this.goalMemberName = goalMemberName;
	}

	public static AssistResponse of(Assist assist) {
		return AssistResponse.builder()
				.otherTeamName(assist.getGoal().getMatch().getOtherTeamName())
				.matchDate(assist.getGoal().getMatch().getDate())
				.goalMemberName(assist.getGoal().getMember().getName())
				.build();
	}
}
