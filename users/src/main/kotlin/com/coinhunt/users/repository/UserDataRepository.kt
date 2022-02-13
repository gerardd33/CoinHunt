package com.coinhunt.users.repository

import com.coinhunt.users.domain.UserData
import org.springframework.data.mongodb.repository.MongoRepository

interface UserDataRepository : MongoRepository<UserData, String> {

    fun findAllByUserId(userId: String): List<UserData>

    fun findAllByEmail(email: String): List<UserData>
}
