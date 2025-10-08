package com.loanapp.domain.shared.valueobject

import java.util.UUID

@JvmInline
value class Id<T> private constructor(
    val value: UUID,
) {
    override fun toString(): String = value.toString()

    companion object {
        fun <T> new(): Id<T> = Id(UUID.randomUUID())

        fun <T> from(uuid: UUID): Id<T> = Id(uuid)

        fun <T> fromString(id: String): Id<T> {
            require(id.isNotBlank()) { "Id cannot be blank" }

            val uuid =
                runCatching { UUID.fromString(id) }
                    .getOrElse { throw IllegalArgumentException("Invalid UUID format: $id") }

            return Id(uuid)
        }
    }
}
