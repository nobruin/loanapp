package com.loanapp.infra.persistencie.customer

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CustomerJpaRepository : JpaRepository<CustomerEntity, UUID> {
    fun findByCpf(cpf: String): CustomerEntity?
}
