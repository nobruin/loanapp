package com.loanapp.shared.api

import com.loanapp.identity.domain.exception.EmailAlreadyExistsException
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    data class ErrorResponse(
        val message: String,
    )

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> =
        ResponseEntity(ErrorResponse(ex.message ?: "Invalid request"), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(ex: ConstraintViolationException): ResponseEntity<ErrorResponse> =
        ResponseEntity(ErrorResponse(ex.message ?: "Validation failed"), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(EmailAlreadyExistsException::class)
    fun handleEmailConflict(ex: EmailAlreadyExistsException): ResponseEntity<ErrorResponse> =
        ResponseEntity(ErrorResponse(ex.message ?: "Email already exists"), HttpStatus.CONFLICT)
}
