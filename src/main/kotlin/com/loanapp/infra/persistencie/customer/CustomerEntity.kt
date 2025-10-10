package com.loanapp.infra.persistencie.customer

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "customer")
class CustomerEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    @Column(name = "external_user_id", nullable = false, unique = true, length = 100)
    val externalUserId: String,
    @Column(unique = true, nullable = false, length = 11)
    val cpf: String,
    @Column(name = "full_name", nullable = false)
    val fullName: String,
    @Column(name = "birth_date", nullable = false)
    val birthDate: LocalDate,
    @Column(nullable = false)
    val address: String,
    @Column(nullable = false)
    val email: String,
    @Column
    val phone: String? = null,
)
