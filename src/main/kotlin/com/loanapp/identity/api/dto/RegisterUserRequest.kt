package com.loanapp.identity.api.dto

data class RegisterUserRequest(
    val email: String,
    val password: String
)