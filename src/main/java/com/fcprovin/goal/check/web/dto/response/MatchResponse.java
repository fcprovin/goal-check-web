package com.fcprovin.goal.check.web.dto.response;

import com.fcprovin.goal.check.domain.match.Match;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MatchResponse {

	private Long id;
    private String otherTeamName;
	private LocalDateTime date;
	private Integer loseCount;
	private Long goalCount;

	@Builder
	public MatchResponse(Long id, String otherTeamName, LocalDateTime date, Integer loseCount) {
		this.id = id;
		this.otherTeamName = otherTeamName;
		this.date = date;
		this.loseCount = loseCount;
	}

	public static MatchResponse of(Match match) {
		return MatchResponse.builder()
				.id(match.getId())
				.otherTeamName(match.getOtherTeamName())
				.date(match.getDate())
				.loseCount(match.getLose())
				.build();
	}
}
