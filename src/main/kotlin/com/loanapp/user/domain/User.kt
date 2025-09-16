package com.loanapp.user.domain

import java.math.BigDecimal
import java.util.UUID

data class User(
    val id: UUID = UUID.randomUUID(),
    val email: String? = null,
    val cpf: String? = null,
    val income: BigDecimal
)