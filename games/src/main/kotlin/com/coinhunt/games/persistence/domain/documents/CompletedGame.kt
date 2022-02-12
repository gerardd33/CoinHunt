package com.coinhunt.games.persistence.domain.documents

import com.coinhunt.games.persistence.domain.components.Difficulty
import com.coinhunt.games.persistence.domain.components.GameStep
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document
data class CompletedGame(
    val difficulty: Difficulty,
    val steps: List<GameStep>,
    val userId: String,
    val totalTimeInMilliseconds: Long,
    val startTime: Instant
)