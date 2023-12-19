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

    private LocalDateTime date;

    @OneToMany(mappedBy = "match")
    private final List<Goal> goals = new ArrayList<>();

    @Builder
    public Match(Long id, String otherTeamName, LocalDateTime date) {
        this.id = id;
        this.otherTeamName = otherTeamName;
        this.date = date;
    }

    public Match(String otherTeamName, LocalDateTime date) {
        this.otherTeamName = otherTeamName;
        this.date = date;
    }
}


