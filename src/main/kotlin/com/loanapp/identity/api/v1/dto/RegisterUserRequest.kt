package com.loanapp.identity.api.v1.dto

data class RegisterUserRequest(
    val email: String,
    val password: String,
)
