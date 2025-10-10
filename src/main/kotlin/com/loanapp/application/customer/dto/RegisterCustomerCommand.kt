package com.loanapp.application.customer.dto

import java.time.LocalDate

data class RegisterCustomerCommand(
    val externalUserId: String,
    val fullName: String,
    val cpf: String,
    val birthDate: LocalDate,
    val email: String,
    val phone: String? = null,
    val address: String,
)
