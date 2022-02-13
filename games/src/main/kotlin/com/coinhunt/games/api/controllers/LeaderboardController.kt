package com.coinhunt.games.api.controllers

import com.coinhunt.games.common.GamesUtils.parseDifficulty
import com.coinhunt.games.persistence.domain.components.CompletedGamesFilter
import com.coinhunt.games.persistence.domain.documents.CompletedGame
import com.coinhunt.games.services.LeaderboardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/leaderboard")
class LeaderboardController(
    @Autowired private val leaderboardService: LeaderboardService
) {

    @GetMapping("/{difficulty}/{filter}")
    fun getLeaderboardTable(
        @PathVariable difficulty: String,
        @PathVariable filter: CompletedGamesFilter
    ): List<CompletedGame> {
        return leaderboardService.calculateLeaderboardTable(parseDifficulty(difficulty), filter)
    }

    @GetMapping("/user/{userId}/{difficulty}/{filter}")
    fun getLeaderboardTableForUser(
        @PathVariable userId: String,
        @PathVariable difficulty: String,
        @PathVariable filter: CompletedGamesFilter
    ): List<CompletedGame> {
        return leaderboardService.calculateLeaderboardTableForUser(userId, parseDifficulty(difficulty), filter)
    }
}
