package com.coinhunt.games.persistence.repositories

import com.coinhunt.games.persistence.domain.documents.CompletedGame
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface CompletedGameRepository : MongoRepository<CompletedGame, String> {

    @Query(value = "{}", sort = "{totalTimeInMilliseconds : 1}")
    fun findAllSortedByLowestTotalTimeInMilliseconds(): List<CompletedGame>
}
