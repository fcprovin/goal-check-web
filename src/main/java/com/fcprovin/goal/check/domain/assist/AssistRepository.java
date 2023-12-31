package com.fcprovin.goal.check.domain.assist;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssistRepository extends JpaRepository<Assist, Long>, AssistQueryRepository {

}
