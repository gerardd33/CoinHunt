package com.coinhunt.games.api.controllers

import com.coinhunt.games.api.dtos.CompletedGameDto
import com.coinhunt.games.api.dtos.LevelInfoDto
import com.coinhunt.games.api.errors.BadRequestException
import com.coinhunt.games.api.mappers.CompletedGameDtoMapper
import com.coinhunt.games.api.mappers.LevelInfoDtoMapper
import com.coinhunt.games.persistence.domain.components.Difficulty
import com.coinhunt.games.services.GameService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/game")
class GameController(
    @Autowired private val gameService: GameService,
    @Autowired private val levelInfoDtoMapper: LevelInfoDtoMapper,
    @Autowired private val completedGameDtoMapper: CompletedGameDtoMapper
) {

    @GetMapping("/info/{difficulty}")
    fun getLevelInfo(@PathVariable difficulty: String): LevelInfoDto {
        val difficultyParsed = Difficulty.values().firstOrNull { it.name == difficulty.uppercase() }
            ?: throw BadRequestException("Difficulty level ${difficulty.uppercase()} does not exist")
        return levelInfoDtoMapper.domainToDto(gameService.retrieveGameMetadata(difficultyParsed))
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveCompletedGame(@RequestBody completedGame: CompletedGameDto): CompletedGameDto {
        gameService.saveCompletedGame(completedGameDtoMapper.dtoToDomain(completedGame))
        return completedGame
    }
}
