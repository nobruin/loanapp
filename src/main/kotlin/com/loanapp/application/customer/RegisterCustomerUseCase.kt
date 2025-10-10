package com.loanapp.application.customer

import com.loanapp.api.customer.dto.RegisterCustomerResult
import com.loanapp.application.customer.dto.RegisterCustomerCommand
import com.loanapp.domain.customer.CustomerRepository
import com.loanapp.infra.persistencie.customer.toDomain
import org.springframework.stereotype.Service

@Service
class RegisterCustomerUseCase(
    private val customerRepository: CustomerRepository,
) {
    fun execute(command: RegisterCustomerCommand): RegisterCustomerResult {
        val customer =
            command.toDomain().apply {
                require(!customerRepository.isEmailInUse(email)) {
                    "Email ${email.value} is already in use"
                }

                require(!customerRepository.isCpfInUse(cpf)) {
                    "CPF ${cpf.value} is already in use"
                }

                require(!customerRepository.existsByExternalUserId(externalUserId)) {
                    "User already linked to a customer"
                }
            }

        customerRepository.save(customer)

        return RegisterCustomerResult(
            id = customer.id.value,
            fullName = customer.fullName,
            cpf = customer.cpf.value,
        )
    }
}
