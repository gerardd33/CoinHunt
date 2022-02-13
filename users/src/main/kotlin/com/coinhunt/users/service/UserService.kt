package com.coinhunt.users.service

import com.coinhunt.users.controller.BadRequestException
import com.coinhunt.users.controller.NotFoundException
import com.coinhunt.users.domain.UserData
import com.coinhunt.users.repository.UserDataRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userDataRepository: UserDataRepository
) {

    fun registerUser(userData: UserData) {
        if (userDataRepository.findAllByUserId(userData.userId).isNotEmpty()) {
            throw BadRequestException("A user with user ID (login) ${userData.userId} already exists")
        }

        if (userDataRepository.findAllByEmail(userData.email).isNotEmpty()) {
            throw BadRequestException("A user with email ${userData.email} already exists")
        }

        userDataRepository.save(userData)
    }

    fun retrieveUserData(userId: String): UserData {
        return userDataRepository.findAllByUserId(userId).firstOrNull()
            ?: throw NotFoundException("User with user ID (login) $userId does not exist")
    }
}
