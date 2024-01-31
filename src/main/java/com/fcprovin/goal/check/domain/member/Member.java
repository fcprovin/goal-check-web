package com.fcprovin.goal.check.domain.member;

import com.fcprovin.goal.check.common.entity.BaseTime;
import com.fcprovin.goal.check.domain.assist.Assist;
import com.fcprovin.goal.check.domain.attend.Attend;
import com.fcprovin.goal.check.domain.goal.Goal;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.EnumType.STRING;
import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String name;

    private String password;

    @Enumerated(value = STRING)
    private MemberType type;

    @OneToMany(mappedBy = "member", cascade = REMOVE)
    private final List<Goal> goals = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = REMOVE)
    private final List<Assist> assists = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = REMOVE)
    private final List<Attend> attends = new ArrayList<>();

    @Builder
    public Member(Long id, String name, MemberType type) {
        this.id = id;
        this.name = name;
        this.type = nonNull(type) ? type : MemberType.MEMBER;
    }

    public Member(String name) {
        this.name = name;
    }

    public Member(String name, MemberType type) {
        this.name = name;
        this.type = type;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
