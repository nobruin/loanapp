package com.loanapp.domain.shared.valueobject

data class Email(
    val value: String,
) {
    init {
        require(value.isNotBlank()) { "Email cannot be blank" }
        require(value.length <= 255) { "Email length must be less than or equal to 255 characters" }
        require(isValid(value)) { "Invalid email: $value" }
    }

    companion object {
        private val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

        fun isValid(email: String): Boolean = regex.matches(email)
    }

    override fun toString(): String = value
}
