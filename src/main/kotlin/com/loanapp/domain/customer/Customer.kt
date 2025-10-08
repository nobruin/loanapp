package com.loanapp.domain.customer

import com.loanapp.domain.shared.valueobject.Cpf
import com.loanapp.domain.shared.valueobject.Email
import com.loanapp.domain.shared.valueobject.Id
import com.loanapp.domain.shared.valueobject.Phone
import java.time.LocalDate

data class Customer(
    val id: Id<Customer>,
    val fullName: String,
    val cpf: Cpf,
    val birthDate: LocalDate,
    val email: Email?,
    val phone: Phone?,
    val address: String,
)