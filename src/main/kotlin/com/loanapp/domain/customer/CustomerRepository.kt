package com.loanapp.domain.customer

import com.loanapp.domain.shared.valueobject.Cpf
import com.loanapp.domain.shared.valueobject.Id

interface CustomerRepository {
    fun save(customer: Customer): Customer
    fun findById(id: Id<Customer>): Customer?
    fun findByCpf(cpf: Cpf): Customer?
}