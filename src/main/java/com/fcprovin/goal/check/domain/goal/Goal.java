package com.fcprovin.goal.check.domain.goal;

import com.fcprovin.goal.check.common.entity.BaseTime;
import com.fcprovin.goal.check.domain.assist.Assist;
import com.fcprovin.goal.check.domain.match.Match;
import com.fcprovin.goal.check.domain.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToOne(mappedBy = "goal")
    private Assist assist;

    @Builder
    public Goal(Long id, Match match, Member member) {
        this.id = id;

        setMember(member);
        setMatch(match);
    }

    public Goal(Match match, Member member) {
        this.match = match;
        this.member = member;
    }

    public void setMember(Member member) {
        this.member = member;
        this.member.getGoals().add(this);
    }

    public void setMatch(Match match) {
    	this.match = match;
        this.match.getGoals().add(this);
    }

}


