package com.loanapp.identity.app

import com.loanapp.identity.domain.AuthUser
import com.loanapp.identity.domain.AuthUserRepository
import com.loanapp.identity.domain.Email
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class LoginUserService(
    private val authUserRepository: AuthUserRepository,
    private val encoder: PasswordEncoder
) {

    fun loginOrThrow(email: String, rawPassword: String): AuthUser {
        val user = authUserRepository.findByEmail(Email(email))
            ?: throw IllegalArgumentException("User not found")

        require(user.verifyPassword(rawPassword, encoder)) {"Invalid credentials"}



        return user
    }
}