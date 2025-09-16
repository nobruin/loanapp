package com.loanapp.identity.app

import com.loanapp.identity.domain.AuthUser
import com.loanapp.identity.domain.AuthUserRepository
import com.loanapp.identity.domain.Email
import com.loanapp.identity.domain.PasswordHasher
import com.loanapp.identity.domain.UserId

class RegisterUserService(
    private val passwordHasher: PasswordHasher,
    private val authUserRepository: AuthUserRepository
) {

    fun register(email: String, password: String): AuthUser{
        val password = passwordHasher.hash(password)
        val emailVo = Email(email)
        authUserRepository.findByEmail(emailVo)

        val existingUser = authUserRepository.findByEmail(emailVo)
        if (existingUser != null) {
            throw IllegalArgumentException("User with email already exists")
        }
        val user = AuthUser(
            id = UserId.new(),
            email = emailVo,
            password = password
        )

        return authUserRepository.save(user)
    }

}