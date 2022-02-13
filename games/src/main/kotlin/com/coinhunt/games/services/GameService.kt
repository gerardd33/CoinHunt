package com.coinhunt.games.services

import com.coinhunt.games.api.errors.NotFoundException
import com.coinhunt.games.persistence.domain.components.Difficulty
import com.coinhunt.games.persistence.domain.documents.CompletedGame
import com.coinhunt.games.persistence.domain.documents.LevelInfo
import com.coinhunt.games.persistence.repositories.CompletedGameRepository
import com.coinhunt.games.persistence.repositories.LevelInfoRepository
import org.springframework.stereotype.Service

@Service
class GameService(
    private val completedGameRepository: CompletedGameRepository,
    private val levelInfoRepository: LevelInfoRepository
) {

    fun retrieveGameMetadata(difficulty: Difficulty): LevelInfo {
        return levelInfoRepository.findOneByDifficulty(difficulty)
            ?: throw NotFoundException("Level info for difficulty $difficulty could not be found")
    }

    fun saveCompletedGame(completedGame: CompletedGame) {
        completedGameRepository.save(completedGame)
    }
}
