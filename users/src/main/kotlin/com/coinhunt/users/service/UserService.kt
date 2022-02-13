package com.coinhunt.users.service

import com.coinhunt.users.domain.UserData
import com.coinhunt.users.repository.UserDataRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userDataRepository: UserDataRepository
) {

    fun registerUser(userData: UserData) {
        if (userDataRepository.findAllByUserId(userData.userId).isNotEmpty()) {
            throw IllegalArgumentException("A user with user ID (login) ${userData.userId} already exists")
        }

        if (userDataRepository.findAllByEmail(userData.email).isNotEmpty()) {
            throw IllegalArgumentException("A user with email ${userData.email} already exists")
        }

        userDataRepository.save(userData)
    }
}
