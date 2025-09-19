package com.loanapp.identity.infra.persistence

import com.loanapp.identity.domain.AuthUser
import com.loanapp.identity.domain.AuthUserRepository
import com.loanapp.identity.domain.Email
import org.springframework.stereotype.Repository

@Repository
class AuthUserRepositoryImpl(
    private val jpaRepository: AuthUserJpaRepository,
) : AuthUserRepository {
    override fun save(user: AuthUser): AuthUser = jpaRepository.save(user.toEntity()).toDomain()

    override fun findByEmail(email: Email): AuthUser? = jpaRepository.findByEmail(email.value)?.toDomain()
}
