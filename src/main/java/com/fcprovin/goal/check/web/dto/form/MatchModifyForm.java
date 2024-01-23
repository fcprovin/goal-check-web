package com.fcprovin.goal.check.web.dto.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@Data
@NoArgsConstructor
public class MatchModifyForm {

    @NotEmpty
    private String otherTeamName;

    @DateTimeFormat(iso = DATE_TIME)
    private LocalDateTime date;

    @NotNull
    private Integer lose;

    @Builder
    public MatchModifyForm(String otherTeamName, LocalDateTime date, Integer lose) {
        this.otherTeamName = otherTeamName;
        this.date = date;
        this.lose = lose;
    }
}
