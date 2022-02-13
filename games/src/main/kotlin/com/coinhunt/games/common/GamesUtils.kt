package com.coinhunt.games.common

import com.coinhunt.games.api.errors.BadRequestException
import com.coinhunt.games.persistence.domain.components.CompletedGamesFilter
import com.coinhunt.games.persistence.domain.components.Difficulty

object GamesUtils {

    fun parseDifficulty(difficultyString: String): Difficulty {
        return Difficulty.values().firstOrNull { it.name == difficultyString.uppercase() }
            ?: throw BadRequestException("Difficulty level ${difficultyString.uppercase()} does not exist")
    }

    fun parseFilter(filterString: String): CompletedGamesFilter = when (filterString.lowercase()) {
        "last" -> CompletedGamesFilter.LAST
        "best-week", "best_week", "bestweek" -> CompletedGamesFilter.BEST_WEEK
        "best-all-time", "best_all_time", "bestalltime" -> CompletedGamesFilter.BEST_ALL_TIME
        else -> throw BadRequestException("Filter $filterString does not exist")
    }
}
