package com.coinhunt.games.api.dtos

import com.coinhunt.games.persistence.domain.components.Difficulty
import com.coinhunt.games.persistence.domain.components.GameStep
import com.coinhunt.games.persistence.domain.components.Maze
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class CompletedGameDto(
    val difficulty: Difficulty,
    val maze: Maze,
    val steps: List<GameStep>,
    val userId: String,
    val totalTimeInMilliseconds: Long,
    val startTimeEpochMilli: Long
)
