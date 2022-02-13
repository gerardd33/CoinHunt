package com.coinhunt.games.api.dtos

import com.coinhunt.games.persistence.domain.components.Difficulty

data class LevelInfoDto(
    val difficulty: Difficulty,
    val description: String,
    val mazeHeight: Int,
    val mazeWidth: Int,
    val numberOfCoins: Int
)
