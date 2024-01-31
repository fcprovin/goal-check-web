package com.fcprovin.goal.check.notification.payload;

import com.fcprovin.goal.check.domain.goal.Goal;
import com.slack.api.model.Field;
import lombok.Data;

import java.util.List;

import static java.lang.String.format;

@Data
public class GoalPayload implements WebhookPayload {

    private final String titleFormat = ":soccer: `%s`님의 골이 `%s`에 등록 되었습니다.";
    private final String color = "#FF0000";
    private final Goal goal;

    public GoalPayload(Goal goal) {
        this.goal = goal;
    }

    public static GoalPayload of(Goal goal) {
        return new GoalPayload(goal);
    }

    @Override
    public String getTitle() {
        return format(titleFormat, goal.getMember().getName(), dateformat(goal.getCreatedDate()));
    }

    @Override
    public List<Field> getFields() {
        return List.of(
                getMatchOtherTeamName(),
                getMatchDate()
        );
    }

    private Field getMatchOtherTeamName() {
        return getField("상대팀명", goal.getMatch().getOtherTeamName());
    }

    private Field getMatchDate() {
        return getField("매치일자", goal.getMatch().getDate());
    }
}
