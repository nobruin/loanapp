package com.loanapp.identity.domain

interface PasswordEncoder {
    fun encode(raw: String): String
    fun matches(raw: String, hash: String): Boolean
}
