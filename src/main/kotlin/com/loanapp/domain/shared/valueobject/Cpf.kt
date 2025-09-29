package com.loanapp.domain.shared.valueobject

data class Cpf(val value: String) {
    init {
        require(isValid(value)) { "Invalid CPF: $value" }
    }

    companion object {
        fun isValid(cpf: String): Boolean {
            val regex = Regex("^\\d{11}$")
            return regex.matches(cpf)
        }
    }

    override fun toString(): String = value
}
