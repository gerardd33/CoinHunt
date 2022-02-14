package com.coinhunt.front.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GameStep {

	private final StepDirection direction;

	private final long millisecondsSinceLastStep;
}
