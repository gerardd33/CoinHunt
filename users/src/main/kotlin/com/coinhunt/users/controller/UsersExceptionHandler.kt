package com.coinhunt.users.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class UsersExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
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