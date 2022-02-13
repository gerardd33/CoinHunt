package com.coinhunt.games.persistence.repositories

import com.coinhunt.games.persistence.domain.components.Difficulty
import com.coinhunt.games.persistence.domain.documents.LevelInfo
import org.springframework.data.mongodb.repository.MongoRepository

interface LevelInfoRepository : MongoRepository<LevelInfo, String> {

    fun findOneByDifficulty(difficulty: Difficulty): LevelInfo?
}
