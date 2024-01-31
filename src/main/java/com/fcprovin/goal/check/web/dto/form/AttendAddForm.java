package com.fcprovin.goal.check.web.dto.form;

import com.fcprovin.goal.check.domain.attend.Attend;
import com.fcprovin.goal.check.domain.match.Match;
import com.fcprovin.goal.check.domain.member.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AttendAddForm {

    @NotEmpty
    private Long id;

    public Attend toEntity(Match match, Member member) {
        return new Attend(match, member);
    }
}
