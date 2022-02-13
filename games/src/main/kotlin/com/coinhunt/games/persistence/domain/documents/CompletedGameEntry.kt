package com.coinhunt.games.persistence.domain.documents

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document
data class CompletedGameEntry(
    val userId: Long,
    val totalTimeInMilliseconds: Long,
    val startTime: Instant,
    @Id val id: ObjectId? = ObjectId.get()
)
