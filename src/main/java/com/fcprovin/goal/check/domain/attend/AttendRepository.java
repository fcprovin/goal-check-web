package com.fcprovin.goal.check.domain.attend;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendRepository extends JpaRepository<Attend, Long>, AttendQueryRepository {

}
