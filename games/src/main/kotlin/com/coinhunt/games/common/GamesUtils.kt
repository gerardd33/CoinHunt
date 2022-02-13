package com.coinhunt.games.common

import com.coinhunt.games.api.errors.BadRequestException
import com.coinhunt.games.persistence.domain.components.Difficulty

object GamesUtils {

    fun parseDifficulty(difficultyString: String): Difficulty {
        return Difficulty.values().firstOrNull { it.name == difficultyString.uppercase() }
            ?: throw BadRequestException("Difficulty level ${difficultyString.uppercase()} does not exist")
    }
}
