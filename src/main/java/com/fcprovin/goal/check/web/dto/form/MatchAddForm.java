package com.fcprovin.goal.check.web.dto.form;

import com.fcprovin.goal.check.domain.match.Match;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchAddForm {

    @NotEmpty
    private String otherTeamName;

    @DateTimeFormat(iso = DATE_TIME)
    private LocalDateTime date;

    public Match toEntity() {
        return new Match(otherTeamName, date);
    }
}
