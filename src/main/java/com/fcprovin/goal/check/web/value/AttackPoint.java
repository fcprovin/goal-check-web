package com.fcprovin.goal.check.web.value;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AttackPoint {

	GOAL(3),
	ASSIST(1);

	private final int point;
}
