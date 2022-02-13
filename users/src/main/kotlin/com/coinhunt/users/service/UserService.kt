package com.coinhunt.users.service

import com.coinhunt.users.api.errors.BadRequestException
import com.coinhunt.users.api.errors.NotFoundException
import com.coinhunt.users.api.errors.UnauthorizedException
import com.coinhunt.users.common.UsersUtils.hashPassword
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

        // TODO return a newly generated JWT
        return "dummy JWT"
    }

    fun authenticateUser(jwt: String): String {
        // TODO decipher the JWT, check the user credentials from the deciphered JWT,
        //  if it's ok, return the user's login, otherwise return HTTP Unauthorized

        return "dummy login"
    }
}
