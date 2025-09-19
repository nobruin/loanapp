package com.loanapp.identity.api.v1.dto

data class LoginResponse(
    val userId: String,
    val email: String,
    val token: String,
)
