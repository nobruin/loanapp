package com.loanapp.identity.domain

import java.time.Instant

class AuthUser(
    val id: UserId,
    val email: Email,
    var password: Password,
    val createdAt: Instant = Instant.now()
) {
    fun verifyPassword(rawPassword: String, encoder: PasswordEncoder): Boolean{
        return password.matches(rawPassword, encoder)
    }
}