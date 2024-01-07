package com.fcprovin.goal.check.web.dto.response;

import com.fcprovin.goal.check.domain.member.Member;
import lombok.Data;

import static com.fcprovin.goal.check.web.value.AttackPoint.ASSIST;
import static com.fcprovin.goal.check.web.value.AttackPoint.GOAL;

@Data
public class MemberResponse {

	private Long id;
    private String name;
	private Long goalCount;
	private Long assistCount;
	private Long attackPoint;

	public MemberResponse(Long id, String name) {
		this.id = id;
		this.name = name;
		this.attackPoint = 0L;
	}

	public static MemberResponse of(Member member) {
		return new MemberResponse(member.getId(), member.getName());
	}

	public void setGoalCount(Long goalCount) {
		this.goalCount = goalCount;
		this.attackPoint += GOAL.getPoint() * goalCount;
	}

	public void setAssistCount(Long assistCount) {
		this.assistCount = assistCount;
		this.attackPoint += ASSIST.getPoint() * assistCount;
	}
}
