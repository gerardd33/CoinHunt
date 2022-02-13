package com.coinhunt.games

import com.coinhunt.games.api.dtos.CompletedGameDto
import com.coinhunt.games.persistence.domain.components.Difficulty
import com.coinhunt.games.persistence.domain.components.GameStep
import com.coinhunt.games.persistence.domain.components.StepDirection
import com.coinhunt.games.persistence.repositories.CompletedGameRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.time.Instant
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class GamesIntegrationTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper,
    @Autowired private val completedGameRepository: CompletedGameRepository
) {

    @Test
    fun `returns sensible metadata for all valid difficulty levels`() {
        val inputs = listOf("easy", "medium", "hard")

        for (difficulty in inputs) {
            mockMvc.get("/game/info/$difficulty")
                .andExpect {
                    status { isOk() }
                    jsonPath("$.difficulty") { value(difficulty.uppercase()) }
                    jsonPath("$.description") { isNotEmpty() }
                    jsonPath("$.mazeHeight") { isNumber() }
                    jsonPath("$.mazeWidth") { isNumber() }
                    jsonPath("$.numberOfCoins") { isNumber() }
                }
        }
    }

    @Test
    fun `saves completed game in the database`() {
        val inputGameSteps = listOf(
            GameStep(StepDirection.LEFT, millisecondsSinceLastStep = 300),
            GameStep(StepDirection.DOWN, millisecondsSinceLastStep = 800),
            GameStep(StepDirection.UP, millisecondsSinceLastStep = 200)
        )
        val input =
            CompletedGameDto(
                Difficulty.MEDIUM, inputGameSteps, userId = UUID.randomUUID().toString(),
                totalTimeInMilliseconds = 303921, startTimeEpochMilli = 1644710875150
            )
        val initialDatabaseCount = completedGameRepository.count()
        if (completedGameRepository.findAllByUserId(input.userId).isNotEmpty()) {
            throw IllegalArgumentException("Invalid test input: there should be no games associated with the given user in the database before the test")
        }

        try {
            mockMvc.post("/game/save") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(input)
            }.andExpect {
                status { isCreated() }
            }

            assertThat(completedGameRepository.count()).isEqualTo(initialDatabaseCount + 1)
            val userGames = completedGameRepository.findAllByUserId(input.userId)
            assertThat(userGames).hasSize(1)
            assertThat(userGames.first().id).isNotNull
            assertThat(userGames.first().difficulty).isEqualTo(input.difficulty)
            assertThat(userGames.first().steps).isEqualTo(input.steps)
            assertThat(userGames.first().userId).isEqualTo(input.userId)
            assertThat(userGames.first().totalTimeInMilliseconds).isEqualTo(input.totalTimeInMilliseconds)
            assertThat(userGames.first().startTime).isEqualTo(Instant.ofEpochMilli(input.startTimeEpochMilli))
        } finally {
            completedGameRepository.deleteByUserId(input.userId)
        }
    }
}