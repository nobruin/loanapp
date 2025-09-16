package com.loanapp.identity.domain

interface AuthUserRepository {
    fun save(user: AuthUser): AuthUser
    fun findByEmail(email: Email): AuthUser?
}