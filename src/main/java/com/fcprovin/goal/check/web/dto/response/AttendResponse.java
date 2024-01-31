package com.fcprovin.goal.check.web.dto.response;

import com.fcprovin.goal.check.domain.attend.Attend;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendResponse {

	private Long id;
	private String memberName;
	private LocalDateTime attendDate;

	@Builder
	public AttendResponse(Long id, String memberName, LocalDateTime attendDate) {
		this.id = id;
		this.memberName = memberName;
		this.attendDate = attendDate;
	}

	public static AttendResponse of(Attend attend) {
		return AttendResponse.builder()
				.id(attend.getId())
				.memberName(attend.getMember().getName())
				.attendDate(attend.getCreatedDate())
				.build();
	}
}
