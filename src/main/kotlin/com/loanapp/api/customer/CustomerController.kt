package com.loanapp.api.customer

import com.loanapp.api.customer.dto.RegisterCustomerRequest
import com.loanapp.application.customer.RegisterCustomerUseCase
import com.loanapp.application.customer.dto.RegisterCustomerCommand
import com.loanapp.application.customer.dto.RegisterCustomerResult
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/customers")
class CustomerController(
    private val createCustomerUseCase: RegisterCustomerUseCase,
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestBody request: RegisterCustomerRequest,
    ): RegisterCustomerResult {
        val externalUserId = jwt.subject

        return createCustomerUseCase.execute(
            RegisterCustomerCommand(
                externalUserId = externalUserId,
                fullName = request.fullName,
                cpf = request.cpf,
                birthDate = request.birthDate,
                email = request.email,
                phone = request.phone,
                address = request.address,
            ),
        )
    }
}
