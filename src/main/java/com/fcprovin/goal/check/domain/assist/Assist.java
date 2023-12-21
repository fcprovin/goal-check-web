package com.fcprovin.goal.check.domain.assist;

import com.fcprovin.goal.check.common.entity.BaseTime;
import com.fcprovin.goal.check.domain.goal.Goal;
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
public class Assist extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "assist_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @Builder
    public Assist(Long id, Member member, Goal goal) {
        this.id = id;
        this.member = member;
        this.goal = goal;
    }

    public Assist(Member member, Goal goal) {
        this.member = member;
        this.goal = goal;
    }
}


