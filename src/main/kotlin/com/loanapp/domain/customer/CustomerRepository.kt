package com.loanapp.domain.customer

interface CustomerRepository {
    fun save(customer: Customer): Customer
    fun findById(id: CustomerId): Customer?
    fun findByCpf(cpf: String): Customer?
}