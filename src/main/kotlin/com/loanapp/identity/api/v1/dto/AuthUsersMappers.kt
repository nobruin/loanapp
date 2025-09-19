package com.loanapp.identity.api.v1.dto

import com.loanapp.identity.domain.AuthUser

fun AuthUser.toResponse(): RegisterUserResponse =
    RegisterUserResponse(
        id = this.id.value.toString(),
        email = this.email.value,
    )
