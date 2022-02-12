package com.coinhunt.games.persistence.domain.documents

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class LevelInfo(
    val description: String,
    val mazeHeight: Int,
    val mazeWidth: Int,
    val numberOfCoins: Int,
    @Id val id: ObjectId = ObjectId.get()
)
