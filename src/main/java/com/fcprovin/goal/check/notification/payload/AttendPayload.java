package com.fcprovin.goal.check.notification.payload;

import com.fcprovin.goal.check.domain.attend.Attend;
import com.slack.api.model.Field;
import lombok.Data;

import java.util.List;

import static java.lang.String.format;

@Data
public class AttendPayload implements WebhookPayload {

    private final String titleFormat = ":white_check_mark: `%s`님이 `%s`에 출석하였습니다.";
    private final String color = "#00FF00";
    private final Attend attend;

    public AttendPayload(Attend attend) {
        this.attend = attend;
    }

    public static AttendPayload of(Attend attend) {
        return new AttendPayload(attend);
    }

    @Override
    public String getTitle() {
        return format(titleFormat, attend.getMember().getName(), dateformat(attend.getCreatedDate()));
    }

    @Override
    public List<Field> getFields() {
        return List.of(
                getMatchOtherTeamName(),
                getMatchDate()
        );
    }

    private Field getMatchOtherTeamName() {
        return getField("상대팀명", attend.getMatch().getOtherTeamName());
    }

    private Field getMatchDate() {
        return getField("매치일자", attend.getMatch().getDate());
    }
}
