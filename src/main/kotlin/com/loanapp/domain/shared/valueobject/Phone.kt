package com.loanapp.domain.shared.valueobject

data class Phone(val value: String) {
    init {
        require(isValid(value)) { "Invalid phone number: $value" }
    }

    companion object {
        fun isValid(phone: String): Boolean {
            val regex = Regex("^\\+?\\d{10,15}\$")
            return regex.matches(phone)
        }
    }

    override fun toString(): String = value
}
