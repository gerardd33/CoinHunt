package com.coinhunt.games.api.controllers

import com.coinhunt.games.persistence.repositories.CompletedGameRepository
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
class LeaderboardControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    @MockkBean
    private lateinit var completedGameRepository: CompletedGameRepository

    @Test
    fun `all endpoints return 400 Bad Request for invalid difficulty levels`() {
        val inputs = listOf("normal", "eas", "hardd")
        every { completedGameRepository.findAllByUserIdSortedByLowestTotalTimeInMilliseconds(any()) } returns listOf()
        every { completedGameRepository.findAllSortedByLowestTotalTimeInMilliseconds() } returns listOf()

        for (difficulty in inputs) {
            for (endpoint in listOf(
                "/leaderboard/$difficulty/by-week",
                "/leaderboard/user/userId/$difficulty/by-week"
            )) {
                mockMvc.get(endpoint)
                    .andExpect {
                        status { isBadRequest() }
                        content { string("Difficulty level ${difficulty.uppercase()} does not exist") }
                    }
            }
        }
    }

    @Test
    fun `all endpoints return 400 Bad Request for invalid completed game filters`() {
        val inputs = listOf("best-two-weeks", "B", "-")
        every { completedGameRepository.findAllByUserIdSortedByLowestTotalTimeInMilliseconds(any()) } returns listOf()
        every { completedGameRepository.findAllSortedByLowestTotalTimeInMilliseconds() } returns listOf()

        for (filter in inputs) {
            for (endpoint in listOf("/leaderboard/medium/$filter", "/leaderboard/user/userId/medium/$filter")) {
                mockMvc.get(endpoint)
                    .andExpect {
                        status { isBadRequest() }
                        content { string("Filter $filter does not exist") }
                    }
            }
        }
    }

    @Test
    fun `all endpoints return 200 OK for valid completed game filters`() {
        val inputs =
            listOf("bestweek", "bestWeek", "best-week", "BESTWEEK", "BEST_WEEK", "best-all-time", "last", "Last")
        every { completedGameRepository.findAllByUserIdSortedByLowestTotalTimeInMilliseconds(any()) } returns listOf()
        every { completedGameRepository.findAllSortedByLowestTotalTimeInMilliseconds() } returns listOf()

        for (filter in inputs) {
            for (endpoint in listOf("/leaderboard/medium/$filter", "/leaderboard/user/userId/medium/$filter")) {
                mockMvc.get(endpoint)
                    .andExpect {
                        status { isOk() }
                    }
            }
        }
    }
}
