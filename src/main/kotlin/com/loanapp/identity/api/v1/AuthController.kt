package com.loanapp.identity.api.v1

import com.loanapp.identity.api.v1.dto.LoginRequest
import com.loanapp.identity.api.v1.dto.LoginResponse
import com.loanapp.identity.api.v1.dto.RegisterUserRequest
import com.loanapp.identity.api.v1.dto.RegisterUserResponse
import com.loanapp.identity.api.v1.dto.toResponse
import com.loanapp.identity.app.LoginUserService
import com.loanapp.identity.app.RegisterUserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/auth")
class AuthController(
    private val registerUserService: RegisterUserService,
    private val loginUserService: LoginUserService
) {

    @PostMapping("register")
    fun register(
        @RequestBody body: RegisterUserRequest
    ): RegisterUserResponse = registerUserService.registerOrThrow(
        body.email,
        body.password
    ).toResponse()

    @PostMapping("login")
    fun login(
        @RequestBody body: LoginRequest
    ): LoginResponse {
        val user = loginUserService.loginOrThrow(body.email, body.password)
        return LoginResponse(
            userId = user.id.value.toString(),
            email =  user.email.value,
            token = "jwt-token"
        )
    }

}