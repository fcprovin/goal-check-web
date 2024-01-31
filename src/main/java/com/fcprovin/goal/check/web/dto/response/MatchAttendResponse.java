package com.fcprovin.goal.check.web.dto.response;

import com.fcprovin.goal.check.domain.match.Match;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MatchAttendResponse {

	private Long id;
    private String otherTeamName;
	private LocalDateTime matchDate;
	private LocalDateTime attendDate;
	private boolean attend;

	@Builder
	public MatchAttendResponse(Long id, String otherTeamName, LocalDateTime matchDate) {
		this.id = id;
		this.otherTeamName = otherTeamName;
		this.matchDate = matchDate;
		this.attend = false;
	}

	public static MatchAttendResponse of(Match match) {
		return MatchAttendResponse.builder()
				.id(match.getId())
				.otherTeamName(match.getOtherTeamName())
				.matchDate(match.getDate())
				.build();
	}
}
