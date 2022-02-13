package com.coinhunt.games.api.controllers

import com.coinhunt.games.api.dtos.LevelInfoDto
import com.coinhunt.games.persistence.domain.components.Difficulty
import com.coinhunt.games.persistence.domain.documents.LevelInfo
import com.coinhunt.games.persistence.repositories.LevelInfoRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockKExtension::class)
class GameControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper
) {

    @MockkBean
    private lateinit var levelInfoRepository: LevelInfoRepository

    @Test
    fun `returns 400 Bad Request for invalid difficulty levels`() {
        val inputs = listOf("normal", "eas", "hardd")

        for (difficulty in inputs) {
            mockMvc.get("/game/info/$difficulty")
                .andExpect {
                    status { isBadRequest() }
                    content { string("Difficulty level ${difficulty.uppercase()} does not exist") }
                }
        }
    }

    @Test
    fun `returns 404 Not Found if no level info in database`() {
        val inputs = listOf("easy", "medium", "hard")
        every { levelInfoRepository.findOneByDifficulty(any()) } returns null

        for (difficulty in inputs) {
            mockMvc.get("/game/info/$difficulty")
                .andExpect {
                    status { isNotFound() }
                    content { string("Level info for difficulty ${difficulty.uppercase()} could not be found") }
                }
        }
    }

    @Test
    fun `returns 400 Bad Request if completed game is in a wrong format`() {
        val input = LevelInfoDto(
            Difficulty.MEDIUM, description = "random description",
            mazeHeight = 2, mazeWidth = 4, numberOfCoins = 5
        )

        mockMvc.post("/game/save") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(input)
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `returns metadata for all valid difficulty levels if level infos are present`() {
        val inputs = listOf("easy", "medium", "hard")
        val difficulties = listOf(Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD)

        for (difficulty in difficulties) {
            val dummyLevelInfo = LevelInfo(
                difficulty, description = "some description",
                mazeHeight = 12, mazeWidth = 30, numberOfCoins = 20
            )
            every { levelInfoRepository.findOneByDifficulty(difficulty) } returns dummyLevelInfo
        }

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
    fun `returns a generated grid for all difficulty levels if level infos are present`() {
        val inputs = listOf("easy", "medium", "hard")
        every { levelInfoRepository.findOneByDifficulty(any()) } returns
                LevelInfo(
                    Difficulty.MEDIUM, description = "some description",
                    mazeHeight = 12, mazeWidth = 30, numberOfCoins = 20
                )

        for (difficulty in inputs) {
            mockMvc.get("/game/new/$difficulty")
                .andExpect {
                    status { isOk() }
                    content { jsonPath("$.grid") { isArray() } }
                }
        }
    }
}
