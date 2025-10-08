package com.loanapp.infra.persistencie.customer

import com.loanapp.domain.customer.Customer
import com.loanapp.domain.shared.valueobject.Cpf
import com.loanapp.domain.shared.valueobject.Email
import com.loanapp.domain.shared.valueobject.Id
import com.loanapp.domain.shared.valueobject.Phone

fun Customer.toEntity() =
    CustomerEntity(
        id = this.id.value,
        fullName = this.fullName,
        cpf = this.cpf.value,
        birthDate = this.birthDate,
        email = this.email?.value,
        phone = this.phone?.value,
        address = this.address,
    )

fun CustomerEntity.toDomain() =
    Customer(
        id = Id.from(this.id),
        fullName = this.fullName,
        cpf = Cpf(this.cpf),
        birthDate = this.birthDate,
        email = this.email?.let { Email(it) },
        phone = this.phone?.let { Phone(it) },
        address = this.address,
    )
