package com.loanapp.identity.domain

interface PasswordHasher {
    fun hash(rawPassword: String): Password
    fun matches(rawPassword: String, password: Password): Boolean
}