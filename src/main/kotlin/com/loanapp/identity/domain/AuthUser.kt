package com.loanapp.identity.domain

import org.springframework.security.crypto.password.PasswordEncoder
import java.time.Instant

class AuthUser(
    val id: UserId,
    val email: Email,
    var password: Password,
    val createdAt: Instant = Instant.now(),
) {
    fun verifyPassword(
        rawPassword: String,
        encoder: PasswordEncoder,
    ): Boolean = password.matches(rawPassword, encoder)
}
