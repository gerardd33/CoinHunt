package com.coinhunt.games.api.errors

sealed class GamesApiException(message: String) : Exception(message)

data class InvalidRequestException(override val message: String) : GamesApiException(message)
