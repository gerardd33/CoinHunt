package com.coinhunt.front.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LevelInfo {

	private final Difficulty difficulty;

	private final String description;

	private final int mazeHeight;

	private final int mazeWidth;

	private final int numberOfCoins;
}
