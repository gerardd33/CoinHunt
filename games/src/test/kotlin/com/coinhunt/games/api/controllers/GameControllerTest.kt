package com.coinhunt.games.api.controllers

import com.coinhunt.games.persistence.repositories.LevelInfoRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockKExtension::class)
class GameControllerTest(
    @Autowired private val mockMvc: MockMvc
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
}
