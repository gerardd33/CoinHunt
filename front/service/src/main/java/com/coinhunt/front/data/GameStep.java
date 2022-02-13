package com.coinhunt.front.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GameStep {

	private final StepDirection stepDirection;

	private final long millisecondsSinceLastStep;
}
