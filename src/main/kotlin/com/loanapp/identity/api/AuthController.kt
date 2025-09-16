package com.loanapp.identity.api

import com.loanapp.identity.api.dto.RegisterUserRequest
import com.loanapp.identity.api.dto.RegisterUserResponse
import com.loanapp.identity.api.dto.toResponse
import com.loanapp.identity.app.RegisterUserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("v1/auth")
class AuthController(
    private val registerUserService: RegisterUserService
) {

    @PostMapping("register")
    fun register(
        @RequestBody body: RegisterUserRequest
    ): RegisterUserResponse = registerUserService.register(
        body.email,
        body.password
    ).toResponse()
}