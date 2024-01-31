package com.fcprovin.goal.check.domain.match;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {

	List<Match> findAllByDateBetweenOrderByCreatedDateDesc(LocalDateTime start, LocalDateTime end);

	List<Match> findAllByDateBetween(LocalDateTime start, LocalDateTime end);
}
