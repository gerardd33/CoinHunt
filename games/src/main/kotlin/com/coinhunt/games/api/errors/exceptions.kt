package com.coinhunt.games.api.errors

sealed class GamesApiException(message: String) : Exception(message)

data class BadRequestException(override val message: String) : GamesApiException(message)

data class NotFoundException(override val message: String) : GamesApiException(message)
