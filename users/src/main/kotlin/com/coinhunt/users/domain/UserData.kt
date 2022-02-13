package com.coinhunt.users.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class UserData(
    @Id val userId: String,
    val email: String,
    val passwordHash: String
)
