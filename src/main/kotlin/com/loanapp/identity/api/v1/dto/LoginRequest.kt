package com.loanapp.identity.api.v1.dto

data class LoginRequest(
    val email: String,
    val password: String,
)
