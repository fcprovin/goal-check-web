package com.fcprovin.goal.check.web.dto.form;

import com.fcprovin.goal.check.domain.assist.Assist;
import com.fcprovin.goal.check.domain.goal.Goal;
import com.fcprovin.goal.check.domain.match.Match;
import com.fcprovin.goal.check.domain.member.Member;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoalAddForm {

    private Long matchId;

    @NotNull
    private Long goalMemberId;

    private Long assistMemberId;

    public GoalAddForm(Long goalMemberId, Long assistMemberId) {
        this.goalMemberId = goalMemberId;
        this.assistMemberId = assistMemberId;
    }

    public Goal ofGoalEntity(Match match, Member member) {
        return new Goal(match, member);
    }

    public Assist ofAssistEntity(Member member, Goal goal) {
        return new Assist(member, goal);
    }
}
