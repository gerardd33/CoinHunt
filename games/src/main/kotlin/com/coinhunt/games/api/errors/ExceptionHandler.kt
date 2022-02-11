package com.coinhunt.games.api.errors

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class RestResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidRequestException::class)
    protected fun handleInvalidRequest(ex: InvalidRequestException): ResponseEntity<String> {
        return ResponseEntity
            .badRequest()
            .body(ex.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleUnexpectedException(ex: Exception): ResponseEntity<Any> {
        return ResponseEntity
            .internalServerError()
            .body(mapOf(Pair("message", UNEXPECTED_ERROR_MESSAGE), Pair("exception", ex)))
    }

    companion object {

        const val UNEXPECTED_ERROR_MESSAGE = "Unexpected error"
    }
}
