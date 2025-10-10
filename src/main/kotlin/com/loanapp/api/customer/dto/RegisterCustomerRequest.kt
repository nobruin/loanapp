package com.loanapp.api.customer.dto

import java.time.LocalDate

class RegisterCustomerRequest(
    val fullName: String,
    val cpf: String,
    val birthDate: LocalDate,
    val email: String,
    val phone: String? = null,
    val address: String,
)
