package com.coinhunt.users.controller

import com.coinhunt.users.domain.UserData
import com.coinhunt.users.repository.UserDataRepository
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
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockKExtension::class)
class UserControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper
) {

    @MockkBean
    private lateinit var userDataRepository: UserDataRepository

    @Test
    fun `returns 201 Created during registration if user with the same login or email does not exists already in the database`() {
        val input = UserData(userId = "userxx1", email = "xxuser@gmail.com", passwordHash = "dj32k4fgs9k")
        every { userDataRepository.findAllByUserId("userxx1") } returns listOf()
        every { userDataRepository.findAllByEmail("xxuser@gmail.com") } returns listOf()
        every { userDataRepository.save(any()) } returns input

        mockMvc.post("/user/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(input)
        }.andExpect {
            status { isCreated() }
        }
    }

    @Test
    fun `returns 400 Bad Request during registration if user with the same login already exists in the database`() {
        val input = UserData(userId = "userxx1", email = "xxuser2@gmail.com", passwordHash = "dj32k4fgs9k")
        every { userDataRepository.findAllByUserId("userxx1") } returns
                listOf(UserData("userxx1", "-", "-"))
        every { userDataRepository.findAllByEmail("xxuser2@gmail.com") } returns listOf()

        mockMvc.post("/user/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(input)
        }.andExpect {
            status { isBadRequest() }
            content { string("A user with user ID (login) ${input.userId} already exists") }
        }
    }

    @Test
    fun `returns 400 Bad Request during registration if user with the same email already exists in the database`() {
        val input = UserData(userId = "userxx2", email = "xxuser@gmail.com", passwordHash = "dj32k4fgs9k")
        every { userDataRepository.findAllByEmail("xxuser@gmail.com") } returns
                listOf(UserData("-", "xxuser@gmail.com", "-"))
        every { userDataRepository.findAllByUserId("userxx2") } returns listOf()

        mockMvc.post("/user/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(input)
        }.andExpect {
            status { isBadRequest() }
            content { string("A user with email ${input.email} already exists") }
        }
    }
}
