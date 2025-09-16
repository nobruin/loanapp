package com.loanapp.identity.domain

import org.springframework.security.crypto.password.PasswordEncoder

class Password private constructor(private val hash: String) {

    fun matches(rawPassword: String, encoder: PasswordEncoder): Boolean {
        return encoder.matches(rawPassword, hash)
    }

    fun getHash(): String = hash

    companion object {
        fun fromPlainText(rawPassword: String, encoder: PasswordEncoder): Password{
            require(rawPassword.length >= 8) { "Password must be at least 8 characters long" }
            return Password(encoder.encode(rawPassword))
        }
        fun fromHash(hash: String): Password{
            return Password(hash)
        }
    }
}