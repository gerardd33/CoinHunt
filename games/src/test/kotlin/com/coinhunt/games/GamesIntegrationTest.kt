package com.coinhunt.games

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class GamesIntegrationTest(
    @Autowired private val mockMvc: MockMvc
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
}