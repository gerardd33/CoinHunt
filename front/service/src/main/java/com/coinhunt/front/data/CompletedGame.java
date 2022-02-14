package com.coinhunt.front.data;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class CompletedGame {

	private final Difficulty difficulty;

	private final List<GameStep> steps;

	private final String userId;

	private final long totalTimeInMilliseconds;

	private final long startTime;

	private final MazeWrapper maze;
}
