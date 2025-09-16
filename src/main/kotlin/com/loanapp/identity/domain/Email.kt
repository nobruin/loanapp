package com.loanapp.identity.domain

class Email(val value: String) {
    init {
        require(value.isBlank()) {"Email cannot be blank"}
        require(Regex("^[^@]+@[^@]+\\.[^@]+\$").matches(value)) { "Invalid email format" }
    }

    override fun toString(): String =  value
    override fun equals(other: Any?): Boolean = other is Email && other.value == value
    override fun hashCode(): Int = value.hashCode()
}