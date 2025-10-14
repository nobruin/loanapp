package com.loanapp.api.customer.dto

import java.time.LocalDate

data class RegisterCustomerRequest(
    val fullName: String,
    val cpf: String,
    val birthDate: LocalDate,
    val phone: String? = null,
    val address: String,
)
