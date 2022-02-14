package com.coinhunt.games.api.controllers

import com.coinhunt.games.api.dtos.CompletedGameDto
import com.coinhunt.games.api.dtos.LevelInfoDto
import com.coinhunt.games.api.dtos.MazeDto
import com.coinhunt.games.api.mappers.CompletedGameDtoMapper
import com.coinhunt.games.api.mappers.LevelInfoDtoMapper
import com.coinhunt.games.api.mappers.MazeDtoMapper
import com.coinhunt.games.common.GamesUtils.parseDifficulty
import com.coinhunt.games.services.GameService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/game")
class GameController(
    @Autowired private val gameService: GameService,
    @Autowired private val levelInfoDtoMapper: LevelInfoDtoMapper,
    @Autowired private val completedGameDtoMapper: CompletedGameDtoMapper,
    @Autowired private val mazeDtoMapper: MazeDtoMapper
) {

    private val logger: Logger = LoggerFactory.getLogger("GameController")

    @GetMapping("/info/{difficulty}")
    fun getLevelInfo(@PathVariable difficulty: String): LevelInfoDto {
        logger.info("---Handling /info")

        return levelInfoDtoMapper.domainToDto(gameService.retrieveGameMetadata(parseDifficulty(difficulty)))
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveCompletedGame(@RequestBody completedGame: CompletedGameDto): CompletedGameDto {
        gameService.saveCompletedGame(completedGameDtoMapper.dtoToDomain(completedGame))
        return completedGame
    }

    @GetMapping("/new/{difficulty}")
    fun getNewGeneratedMaze(@PathVariable difficulty: String): ResponseEntity<MazeDto> {
        val dto = mazeDtoMapper.domainToDto(gameService.generateNewMaze(parseDifficulty(difficulty)))

        logger.info("---Handling /new")

        return ResponseEntity.ok()
                .header("Cache-Control", "no-cache")
                .body(dto)
    }
}
