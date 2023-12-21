package com.fcprovin.goal.check.domain.goal;

import com.fcprovin.goal.check.common.entity.BaseTime;
import com.fcprovin.goal.check.domain.assist.Assist;
import com.fcprovin.goal.check.domain.match.Match;
import com.fcprovin.goal.check.domain.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Goal extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "goal_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Goal(Long id, Match match, Member member) {
        this.id = id;
        this.match = match;
        this.member = member;
    }

    public Goal(Match match, Member member) {
        this.match = match;
        this.member = member;
    }
}


