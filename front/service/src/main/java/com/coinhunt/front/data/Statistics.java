package com.coinhunt.front.data;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Statistics {

	private final int numberOfGames;

	private final Map<Difficulty, Integer> bestTimesInMilliseconds;

	private final Map<Difficulty, Integer> averageTimesInMilliseconds;
}
