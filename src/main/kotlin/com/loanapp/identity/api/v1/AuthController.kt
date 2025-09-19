package com.loanapp.identity.api.v1

import com.loanapp.identity.api.v1.dto.LoginRequest
import com.loanapp.identity.api.v1.dto.LoginResponse
import com.loanapp.identity.api.v1.dto.RegisterUserRequest
import com.loanapp.identity.api.v1.dto.RegisterUserResponse
import com.loanapp.identity.api.v1.dto.toResponse
import com.loanapp.identity.app.LoginUserService
import com.loanapp.identity.app.RegisterUserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/auth")
@Tag(name = "Authentication", description = "Endpoints for user registration and authentication")
class AuthController(
    private val registerUserService: RegisterUserService,
    private val loginUserService: LoginUserService
) {

    @PostMapping("register")
    @Operation(
        summary = "Register a new user",
        description = "Creates a new user with a unique email and encrypted password.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "User registered successfully",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = RegisterUserResponse::class))]
            ),
            ApiResponse(responseCode = "409", description = "Email already exists", content = [Content()]),
            ApiResponse(responseCode = "400", description = "Invalid request payload", content = [Content()])
        ]
    )
    fun register(
        @RequestBody body: RegisterUserRequest
    ): RegisterUserResponse = registerUserService.registerOrThrow(
        body.email,
        body.password
    ).toResponse()

    @PostMapping("login")
    @Operation(
        summary = "Login user",
        description = "Authenticates the user and returns a JWT token.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Authenticated",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = LoginResponse::class))]
            ),
            ApiResponse(responseCode = "401", description = "Invalid credentials", content = [Content()])
        ]
    )
    fun login(
        @RequestBody body: LoginRequest
    ): LoginResponse {
        val (token, userId) = loginUserService.loginOrThrow(body.email, body.password)
        return LoginResponse(
            userId = userId.value.toString(),
            email =  body.email,
            token = token
        )
    }

}