package com.fcprovin.goal.check.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByType(MemberType type);

    Optional<Member> findByNameAndType(String name, MemberType type);
}
