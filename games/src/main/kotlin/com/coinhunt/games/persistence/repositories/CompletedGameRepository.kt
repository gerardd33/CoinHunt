package com.coinhunt.games.persistence.repositories

import com.coinhunt.games.persistence.domain.documents.CompletedGame
import org.springframework.data.mongodb.repository.MongoRepository

interface CompletedGameRepository : MongoRepository<CompletedGame, String> {

    fun deleteByUserId(userId: String)

    fun findAllByUserId(userId: String): List<CompletedGame>
}
