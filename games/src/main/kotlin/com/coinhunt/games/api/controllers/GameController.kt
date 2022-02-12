package com.coinhunt.games.api.controllers

import com.coinhunt.games.api.errors.InvalidRequestException
import com.coinhunt.games.persistence.domain.components.Difficulty
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/game")
class GameController {

    // TODO replace dummy functionality
    @GetMapping("/info/{difficulty}")
    fun getLevelInfo(@PathVariable difficulty: String): String = when (difficulty) {
        Difficulty.EASY.toString().lowercase() -> "Easy mode"
        Difficulty.MEDIUM.toString().lowercase() -> "Medium mode"
        Difficulty.HARD.toString().lowercase() -> "Hard mode"
        else -> throw InvalidRequestException("Unknown difficulty: $difficulty")
    }
}
