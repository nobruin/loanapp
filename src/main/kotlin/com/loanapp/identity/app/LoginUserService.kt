package com.loanapp.identity.app

import com.loanapp.identity.domain.AuthUserRepository
import com.loanapp.identity.domain.Email
import com.loanapp.identity.domain.TokenProvider
import com.loanapp.identity.domain.UserId
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class LoginUserService(
    private val authUserRepository: AuthUserRepository,
    private val encoder: PasswordEncoder,

    private val tokenProvider: TokenProvider,
) {
    fun loginOrThrow(
        email: String,
        rawPassword: String,
    ): Pair<String, UserId> {
        val user =
            authUserRepository.findByEmail(Email(email))
                ?: throw IllegalArgumentException("User not found")

        require(user.verifyPassword(rawPassword, encoder)) { "Invalid credentials" }

        val token = tokenProvider.generateToken(user.id)
        return token to user.id
    }
}
