package com.coinhunt.games.persistence.domain.documents

import com.coinhunt.games.persistence.domain.components.Difficulty
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Statistics(
    val numberOfGames: Long,
    val bestTimesInMilliseconds: Map<Difficulty, Long>,
    val averageTimesInMilliseconds: Map<Difficulty, Long>,
    @Id val id: ObjectId? = ObjectId.get()
)
