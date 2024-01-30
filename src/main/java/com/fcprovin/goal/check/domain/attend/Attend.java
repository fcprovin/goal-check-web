package com.fcprovin.goal.check.domain.attend;

import com.fcprovin.goal.check.common.entity.BaseTime;
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
public class Attend extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "attend_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Attend(Long id, Match match, Member member) {
        this.id = id;

        setMatch(match);
        setMember(member);
    }

    public Attend(Match match, Member member) {
        this.match = match;
        this.member = member;
    }

    public void setMember(Member member) {
        this.member = member;
        this.member.getAttends().add(this);
    }

    public void setMatch(Match match) {
    	this.match = match;
        this.match.getAttends().add(this);
    }
}
