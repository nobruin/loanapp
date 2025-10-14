package com.loanapp.api.customer.dto

import java.util.UUID

class RegisterCustomerResult(
    val id: UUID,
    val fullName: String,
    val cpf: String,
)
