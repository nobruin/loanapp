package com.loanapp.infra.persistencie.customer

import com.loanapp.application.customer.dto.RegisterCustomerCommand
import com.loanapp.domain.customer.Customer
import com.loanapp.domain.shared.valueobject.Cpf
import com.loanapp.domain.shared.valueobject.Email
import com.loanapp.domain.shared.valueobject.Id
import com.loanapp.domain.shared.valueobject.Phone

fun Customer.toEntity() =
    CustomerEntity(
        id = this.id.value,
        externalUserId = this.externalUserId,
        fullName = this.fullName,
        cpf = this.cpf.value,
        birthDate = this.birthDate,
        email = this.email.value,
        phone = this.phone?.value,
        address = this.address,
    )

fun CustomerEntity.toDomain() =
    Customer(
        id = Id.from(this.id),
        externalUserId = this.externalUserId,
        fullName = this.fullName,
        cpf = Cpf(this.cpf),
        birthDate = this.birthDate,
        email = Email(this.email),
        phone = this.phone?.let(::Phone),
        address = this.address,
    )

fun RegisterCustomerCommand.toDomain() =
    Customer(
        id = Id.new(),
        externalUserId = this.externalUserId,
        fullName = this.fullName,
        cpf = Cpf(this.cpf),
        birthDate = this.birthDate,
        email = Email(this.email),
        phone = this.phone?.let(::Phone),
        address = this.address,
    )
