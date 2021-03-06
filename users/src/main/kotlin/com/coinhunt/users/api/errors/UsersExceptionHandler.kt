package com.coinhunt.users.api.errors

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class UsersExceptionHandler {

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequest(ex: BadRequestException): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ex.message)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(ex: NotFoundException): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ex.message)
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorized(ex: UnauthorizedException): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ex.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleUnexpectedException(ex: Exception): ResponseEntity<Any> {
        return ResponseEntity
            .internalServerError()
            .body(mapOf(Pair("message", UNEXPECTED_ERROR_MESSAGE), Pair("reason", ex.message), Pair("exception", ex)))
    }

    companion object {

        const val UNEXPECTED_ERROR_MESSAGE = "Unexpected error"
    }
}
