package com.loanapp.infra.persistencie.customer

import com.loanapp.domain.customer.Customer
import com.loanapp.domain.customer.CustomerRepository
import com.loanapp.domain.shared.valueobject.Cpf
import com.loanapp.domain.shared.valueobject.Email
import com.loanapp.domain.shared.valueobject.Id
import org.springframework.stereotype.Repository

@Repository
class CustomerRepositoryImpl(
    private val jpaRepository: CustomerJpaRepository,
) : CustomerRepository {
    override fun save(customer: Customer): Customer = jpaRepository.save(customer.toEntity()).toDomain()

    override fun findById(id: Id<Customer>): Customer? = jpaRepository.findById(id.value).orElse(null)?.toDomain()

    override fun findByCpf(cpf: Cpf): Customer? = jpaRepository.findByCpf(cpf.value)?.toDomain()

    override fun isCpfInUse(cpf: Cpf): Boolean = jpaRepository.existsByCpf(cpf.value)

    override fun isEmailInUse(email: Email): Boolean = jpaRepository.existsByEmail(email.value)

    override fun existsByExternalUserId(externalUserId: String): Boolean = jpaRepository.existsByExternalUserId(externalUserId)
}
