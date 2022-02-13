package com.coinhunt.users.api.errors

sealed class UsersApiException(message: String) : Exception(message)

data class BadRequestException(override val message: String) : UsersApiException(message)

data class NotFoundException(override val message: String) : UsersApiException(message)

data class UnauthorizedException(override val message: String) : UsersApiException(message)
