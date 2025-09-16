package com.loanapp.identity.app

import com.loanapp.identity.domain.AuthUser
import com.loanapp.identity.domain.AuthUserRepository
import com.loanapp.identity.domain.Email
import com.loanapp.identity.domain.Password
import com.loanapp.identity.domain.UserId
import com.loanapp.identity.domain.exception.EmailAlreadyExistsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class RegisterUserService(
    private val encoder: PasswordEncoder,
    private val authUserRepository: AuthUserRepository
) {

    fun register(email: String, rawPassword: String): AuthUser{
        val emailVo = Email(email)

        authUserRepository.findByEmail(emailVo)?.let {
            throw EmailAlreadyExistsException("User with email: $email already exists")
        }

        val password = Password.fromPlainText(rawPassword, encoder)
        val user = AuthUser(
            id = UserId.new(),
            email = emailVo,
            password = password
        )

        return authUserRepository.save(user)
    }

}