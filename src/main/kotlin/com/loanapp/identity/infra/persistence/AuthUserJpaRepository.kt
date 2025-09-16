package com.loanapp.identity.infra.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface AuthUserJpaRepository: JpaRepository<AuthUserEntity, String>{
    fun findByEmail(email: String): AuthUserEntity?
}