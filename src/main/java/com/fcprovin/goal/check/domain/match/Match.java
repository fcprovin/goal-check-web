package com.fcprovin.goal.check.domain.match;

import com.fcprovin.goal.check.common.entity.BaseTime;
import com.fcprovin.goal.check.domain.goal.Goal;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Match extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "match_id")
    private Long id;

    private String otherTeamName;

    private Integer lose;

    private LocalDateTime date;

    @OneToMany(mappedBy = "match")
    private final List<Goal> goals = new ArrayList<>();

    @Builder
    public Match(String otherTeamName, LocalDateTime date) {
        this.otherTeamName = otherTeamName;
        this.date = date;
        this.lose = 0;
    }

    public void setLose(Integer lose) {
        this.lose = lose;
    }
}


