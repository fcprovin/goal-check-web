package com.fcprovin.goal.check.web.dto.response;

import com.fcprovin.goal.check.domain.match.Match;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MatchDetailResponse {

	private Long id;
    private String otherTeamName;
	private Integer lose;
	private LocalDateTime date;
	private List<GoalResponse> goals;

	@Builder
	public MatchDetailResponse(Long id, String otherTeamName, Integer lose, LocalDateTime date) {
		this.id = id;
		this.otherTeamName = otherTeamName;
		this.lose = lose;
		this.date = date;
	}

	public static MatchDetailResponse of(Match match) {
		return MatchDetailResponse.builder()
				.id(match.getId())
				.otherTeamName(match.getOtherTeamName())
				.lose(match.getLose())
				.date(match.getDate())
				.build();
	}
}
