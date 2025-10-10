package com.loanapp.application.customer.dto

import java.util.UUID

class RegisterCustomerResult(
    val id: UUID,
    val fullName: String,
    val cpf: String,
)
