package com.coinhunt.games.persistence.domain.documents

import com.coinhunt.games.persistence.domain.components.Difficulty

data class Statistics(
    val numberOfGames: Long,
    val bestTimesInMilliseconds: Map<Difficulty, Long>,
    val averageTimesInMilliseconds: Map<Difficulty, Long>
)
