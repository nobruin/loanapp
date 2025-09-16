package com.loanapp.identity.infra.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "auth_users")
data class AuthUserEntity(
    @Id
    val id: UUID,
    @Column(nullable = false, unique = true)
    val email: String,
    @Column(nullable = false)
    val password: String
)

