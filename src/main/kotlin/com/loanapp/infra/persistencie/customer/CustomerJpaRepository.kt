package com.loanapp.infra.persistencie.customer

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CustomerJpaRepository : JpaRepository<CustomerEntity, UUID> {
    fun findByCpf(cpf: String): CustomerEntity?

    fun existsByCpf(cpf: String): Boolean

    fun existsByEmail(email: String): Boolean

    fun existsByExternalUserId(externalUserId: String): Boolean
}
