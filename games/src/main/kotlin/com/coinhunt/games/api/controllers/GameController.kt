package com.coinhunt.games.api.controllers

import com.coinhunt.games.common.Constants.Difficulty.EASY
import com.coinhunt.games.common.Constants.Difficulty.HARD
import com.coinhunt.games.common.Constants.Difficulty.MEDIUM
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
        EASY -> "Easy mode"
        MEDIUM -> "Medium mode"
        HARD -> "Hard mode"
        else -> throw IllegalArgumentException("Unknown difficulty: $difficulty") // TODO return "bad request"
    }
}