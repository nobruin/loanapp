package com.loanapp.domain.customer

import com.loanapp.domain.shared.valueobject.Cpf
import com.loanapp.domain.shared.valueobject.Email
import com.loanapp.domain.shared.valueobject.Id

interface CustomerRepository {
    fun save(customer: Customer): Customer

    fun findById(id: Id<Customer>): Customer?

    fun findByCpf(cpf: Cpf): Customer?

    fun isCpfInUse(cpf: Cpf): Boolean

    fun isEmailInUse(email: Email): Boolean

    fun existsByExternalUserId(externalUserId: String): Boolean
}
