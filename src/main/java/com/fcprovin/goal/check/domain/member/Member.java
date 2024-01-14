package com.fcprovin.goal.check.domain.member;

import com.fcprovin.goal.check.common.entity.BaseTime;
import com.fcprovin.goal.check.domain.assist.Assist;
import com.fcprovin.goal.check.domain.goal.Goal;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.REMOVE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseTime {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String password;

    @OneToMany(mappedBy = "member", cascade = REMOVE)
    private final List<Goal> goals = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = REMOVE)
    private final List<Assist> assists = new ArrayList<>();

    @Builder
    public Member(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Member(String name) {
        this.name = name;
    }
}
