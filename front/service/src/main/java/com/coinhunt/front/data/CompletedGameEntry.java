package com.coinhunt.front.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CompletedGameEntry {

	private final String userId;

	private final long totalTimeInMilliseconds;

	private final long startTimeEpochMilli;
}
