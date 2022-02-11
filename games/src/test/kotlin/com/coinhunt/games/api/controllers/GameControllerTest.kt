package com.coinhunt.games.api.controllers

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class GameControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    @Test
    fun `returns dummy info for valid difficulty levels`() {
        val inputs = listOf(Pair("easy", "Easy mode"), Pair("medium", "Medium mode"), Pair("hard", "Hard mode"))

        for ((difficulty, expectedResponse) in inputs) {
            mockMvc.perform(get("/game/info/$difficulty"))
                .andExpect(status().isOk)
                .andExpect(content().string(expectedResponse))
        }
    }

    @Test
    fun `returns 400 Bad Request for invalid difficulty levels`() {
        val inputs = listOf("normal", "eas", "hardd")

        for (difficulty in inputs) {
            mockMvc.perform(get("/game/info/$difficulty"))
                .andExpect(status().isBadRequest)
                .andExpect(content().string("Unknown difficulty: $difficulty"))
        }
    }
}
