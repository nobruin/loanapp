package com.loanapp.identity.domain

import java.util.UUID

@JvmInline
value class UserId(
    val value: UUID,
) {
    companion object {
        fun new(): UserId = UserId(UUID.randomUUID())
    }
}
