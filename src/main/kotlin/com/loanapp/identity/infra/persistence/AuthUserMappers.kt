package com.loanapp.identity.infra.persistence

import com.loanapp.identity.domain.AuthUser
import com.loanapp.identity.domain.Email
import com.loanapp.identity.domain.Password
import com.loanapp.identity.domain.UserId

fun  AuthUser.toEntity(): AuthUserEntity =
    AuthUserEntity(
        id = this.id.value,
        password = this.password.getHash(),
        email = this.email.value
    )

fun AuthUserEntity.toDomain(): AuthUser =
    AuthUser(
        id = UserId(this.id),
        email = Email(this.email),
        password = Password.fromHash(this.password)
    )