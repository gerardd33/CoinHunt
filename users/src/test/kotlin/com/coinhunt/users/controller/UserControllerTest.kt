package com.coinhunt.users.controller

import com.coinhunt.users.common.SecurityUtils
import com.coinhunt.users.domain.UserCredentials
import com.coinhunt.users.domain.UserData
import com.coinhunt.users.repository.UserDataRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.schema.JsonSchemaProperty.string
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
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
        val input = UserData(userId = "userxx1", email = "xxuser@gmail.com", password = "dj32k4fgs9k")
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
        val input = UserData(userId = "userxx1", email = "xxuser2@gmail.com", password = "dj32k4fgs9k")
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
        val input = UserData(userId = "userxx2", email = "xxuser@gmail.com", password = "dj32k4fgs9k")
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

    @Test
    fun `returns user data if user exists in the database`() {
        val userData = UserData(userId = "__john", email = "john__@gmail.com", password = "fjsdi932m")
        every { userDataRepository.findAllByUserId("__john") } returns listOf(userData)

        mockMvc.get("/user/data/${userData.userId}")
            .andExpect {
                status { isOk() }
                jsonPath("$.userId") { string(userData.userId) }
                jsonPath("$.email") { string(userData.email) }
                jsonPath("$.password") { string(userData.password) }
            }
    }

    @Test
    fun `returns 404 Not Found if user does not exist in the database`() {
        val input = UserData(userId = "userxx2", email = "xxuser@gmail.com", password = "dj32k4fgs9k")
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

    @Test
    fun `returns 401 Unauthorized if passed JWT token is invalid`() {
        val input = "382nfmcre9932fmclfdxc32kd932k9d9k2391mmdj29"
        every { userDataRepository.findAllByUserId(any()) } returns listOf(UserData("", "", ""))

        mockMvc.post("/user/authenticate") {
            content = input
        }.andExpect {
            status { isUnauthorized() }
        }
    }

    @Test
    fun `returns 401 Unauthorized if password for user in incorrect`() {
        val input = UserCredentials("someUser", "12345")
        every { userDataRepository.findAllByUserId("someUser") } returns
                listOf(UserData("someUser", "someUser@gmail.com", "xxxx"))

        mockMvc.post("/user/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(input)
        }.andExpect {
            status { isUnauthorized() }
        }
    }

    @Test
    fun `returns a newly generated JWT token if password for user in correct`() {
        val password = "12345"
        val passwordHash = SecurityUtils.hashPassword(password)
        val userId = "someUser"
        val input = UserCredentials(userId, password)
        every { userDataRepository.findAllByUserId(userId) } returns
                listOf(UserData(userId, "someUser@gmail.com", passwordHash))

        val responseBody = mockMvc.post("/user/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(input)
        }.andExpect {
            status { isOk() }
        }.andReturn().response.contentAsString

        assertThat(responseBody).isNotBlank
    }
}
