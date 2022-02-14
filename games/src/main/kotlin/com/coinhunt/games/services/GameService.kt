package com.coinhunt.games.services

import com.coinhunt.games.api.errors.NotFoundException
import com.coinhunt.games.persistence.domain.components.Difficulty
import com.coinhunt.games.persistence.domain.components.FieldContent
import com.coinhunt.games.persistence.domain.components.Maze
import com.coinhunt.games.persistence.domain.documents.CompletedGame
import com.coinhunt.games.persistence.domain.documents.LevelInfo
import com.coinhunt.games.persistence.repositories.CompletedGameRepository
import com.coinhunt.games.persistence.repositories.LevelInfoRepository
import org.springframework.stereotype.Service

@Service
class GameService(
    private val completedGameRepository: CompletedGameRepository,
    private val levelInfoRepository: LevelInfoRepository,
) {

    fun retrieveGameMetadata(difficulty: Difficulty): LevelInfo {
        return levelInfoRepository.findOneByDifficulty(difficulty)
            ?: throw NotFoundException("Level info for difficulty $difficulty could not be found")
    }

    fun saveCompletedGame(completedGame: CompletedGame) {
        completedGameRepository.save(completedGame)
    }

    fun generateNewMaze(difficulty: Difficulty): Maze {
        val difficultyLevelInfo = levelInfoRepository.findOneByDifficulty(difficulty)
            ?: throw NotFoundException("Level info for difficulty $difficulty could not be found")

        // TODO implement real generation algorithm
        val generatedGrid = (1..difficultyLevelInfo.mazeHeight).map { row ->
            (1..difficultyLevelInfo.mazeWidth).map { column ->
                generateMazeField(row, column)
            }
        }

        return Maze(generatedGrid)
    }

    private fun generateMazeField(row: Int, column: Int): FieldContent {
        return if (row == 1 && column == 1) FieldContent.PLAYER
        else {
            val randomNumber = (0..100).random()

            if (randomNumber in 1..5) FieldContent.COIN
            else if (randomNumber in 5..10) FieldContent.WALL
            else FieldContent.EMPTY
        }
    }
}
