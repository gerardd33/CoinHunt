package com.coinhunt.games.services

import com.coinhunt.games.persistence.domain.components.CompletedGamesFilter
import com.coinhunt.games.persistence.domain.components.Difficulty
import com.coinhunt.games.persistence.domain.documents.CompletedGame
import com.coinhunt.games.persistence.repositories.CompletedGameRepository
import org.springframework.stereotype.Service

@Service
class LeaderboardService(
    private val completedGameRepository: CompletedGameRepository
) {

    companion object {

        const val LEADERBOARD_TABLE_SIZE = 10
    }

    fun calculateLeaderboardTable(difficulty: Difficulty, filter: CompletedGamesFilter): List<CompletedGame> {
        return completedGameRepository.findAllSortedByLowestTotalTimeInMilliseconds()
            .take(LEADERBOARD_TABLE_SIZE)
    }
}
