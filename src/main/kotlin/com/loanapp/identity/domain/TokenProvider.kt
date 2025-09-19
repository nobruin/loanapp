package com.loanapp.identity.domain

interface TokenProvider {
    fun generateToken(userId: UserId): String

    fun validateToken(token: String): Boolean

    fun extractUserId(token: String): UserId?
}
