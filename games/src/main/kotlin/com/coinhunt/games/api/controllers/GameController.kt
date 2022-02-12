package com.coinhunt.games.api.controllers

import com.coinhunt.games.api.errors.BadRequestException
import com.coinhunt.games.persistence.domain.components.Difficulty
import com.coinhunt.games.persistence.domain.documents.LevelInfo
import com.coinhunt.games.services.GameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/game")
class GameController(
    @Autowired private val gameService: GameService
) {

    // TODO return DTO instead
    @GetMapping("/info/{difficulty}")
    fun getLevelInfo(@PathVariable difficulty: String): LevelInfo {
        val difficultyParsed = Difficulty.values().firstOrNull { it.name == difficulty.uppercase() }
            ?: throw BadRequestException("Difficulty level ${difficulty.uppercase()} does not exist")
        return gameService.retrieveGameMetadata(difficultyParsed)
    }
}
