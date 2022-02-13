package com.coinhunt.users.service

import com.coinhunt.users.api.errors.BadRequestException
import com.coinhunt.users.api.errors.NotFoundException
import com.coinhunt.users.api.errors.UnauthorizedException
import com.coinhunt.users.common.SecurityUtils.decodeJwtToken
import com.coinhunt.users.common.SecurityUtils.encodeJwtToken
import com.coinhunt.users.common.SecurityUtils.hashPassword
import com.coinhunt.users.domain.UserCredentials
import com.coinhunt.users.domain.UserData
import com.coinhunt.users.repository.UserDataRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userDataRepository: UserDataRepository
) {

    fun registerUser(userData: UserData) {
        val userDataWithHashedPassword = userData.copy(passwordHash = hashPassword(userData.passwordHash))

        if (userDataRepository.findAllByUserId(userData.userId).isNotEmpty()) {
            throw BadRequestException("A user with user ID (login) ${userData.userId} already exists")
        }

        if (userDataRepository.findAllByEmail(userData.email).isNotEmpty()) {
            throw BadRequestException("A user with email ${userData.email} already exists")
        }

        userDataRepository.save(userDataWithHashedPassword)
    }

    fun retrieveUserData(userId: String): UserData {
        return userDataRepository.findAllByUserId(userId).firstOrNull()
            ?: throw NotFoundException("User with user ID (login) $userId does not exist")
    }

    fun loginUser(userCredentials: UserCredentials): String {
        val hashedPassword = hashPassword(userCredentials.password)
        val storedDataForUser = retrieveUserData(userCredentials.userId)

        if (hashedPassword != storedDataForUser.passwordHash) {
            throw UnauthorizedException("Entered password is incorrect: ${userCredentials.password}")
        }

        return encodeJwtToken(userCredentials.userId)
    }

    fun authenticateUser(jwtToken: String): String {
        val userId = decodeJwtToken(jwtToken)
        if (userDataRepository.findAllByUserId(userId).isEmpty()) {
            throw UnauthorizedException("User with ID $userId is not registered")
        }

        return userId
    }
}
