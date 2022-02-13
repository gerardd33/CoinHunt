package com.coinhunt.games.persistence.domain.documents

import com.coinhunt.games.persistence.domain.components.Difficulty
import com.coinhunt.games.persistence.domain.components.GameStep
import com.coinhunt.games.persistence.domain.components.Maze
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document
data class CompletedGame(
    val difficulty: Difficulty,
    val maze: Maze,
    val steps: List<GameStep>,
    val userId: String,
    val totalTimeInMilliseconds: Long,
    val startTime: Instant,
    @Id val id: ObjectId? = ObjectId.get()
)
