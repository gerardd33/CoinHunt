package com.coinhunt.users.repository

import com.coinhunt.users.domain.UserData
import org.springframework.data.mongodb.repository.MongoRepository

interface UserDataRepository : MongoRepository<UserData, String>
