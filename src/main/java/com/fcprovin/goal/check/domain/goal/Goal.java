package com.fcprovin.goal.check.domain.goal;

import com.fcprovin.goal.check.common.entity.BaseTime;
import com.fcprovin.goal.check.domain.assist.Assist;
import com.fcprovin.goal.check.domain.match.Match;
import com.fcprovin.goal.check.domain.member.Member;
import jakarta.persistence.*;
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
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "assist_id")
    private Assist assist;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "match_id")
    private Match match;
}


