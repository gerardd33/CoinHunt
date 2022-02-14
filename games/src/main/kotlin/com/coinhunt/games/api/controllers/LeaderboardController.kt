package com.coinhunt.games.api.controllers

import com.coinhunt.games.api.dtos.CompletedGameDto
import com.coinhunt.games.api.mappers.CompletedGameDtoMapper
import com.coinhunt.games.common.GamesUtils.parseDifficulty
import com.coinhunt.games.common.GamesUtils.parseFilter
import com.coinhunt.games.services.LeaderboardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/leaderboard")
class LeaderboardController(
    @Autowired private val leaderboardService: LeaderboardService,
    @Autowired private val completedGameDtoMapper: CompletedGameDtoMapper
) {

    @GetMapping("/{difficulty}/{filter}")
    fun getLeaderboardTable(
        @PathVariable difficulty: String,
        @PathVariable filter: String
    ): List<CompletedGameDto> {
        val results = leaderboardService.calculateLeaderboardTable(parseDifficulty(difficulty), parseFilter(filter))
        return results.map { completedGameDtoMapper.domainToDto(it) }
    }

    @GetMapping("/user/{userId}/{difficulty}/{filter}")
    fun getLeaderboardTableForUser(
        @PathVariable userId: String,
        @PathVariable difficulty: String,
        @PathVariable filter: String
    ): List<CompletedGameDto> {
        val results = leaderboardService.calculateLeaderboardTableForUser(
                userId,
                parseDifficulty(difficulty),
                parseFilter(filter)
        )

        return results.map { completedGameDtoMapper.domainToDto(it) }
    }
}
