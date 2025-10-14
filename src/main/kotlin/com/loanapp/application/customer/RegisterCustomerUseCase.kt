package com.loanapp.application.customer

import com.loanapp.api.customer.dto.RegisterCustomerResult
import com.loanapp.application.customer.dto.RegisterCustomerCommand
import com.loanapp.domain.customer.CustomerRepository
import com.loanapp.infra.persistencie.customer.toDomain
import org.springframework.stereotype.Service

@Service
class RegisterCustomerUseCase(
    private val customerRepository: CustomerRepository,
    private val validations: List<CustomerRegisterValidation>,
) {
    fun execute(command: RegisterCustomerCommand): RegisterCustomerResult {
        // Run all validations using the strategy pattern
        validations.forEach { validation ->
            validation.perform(command, customerRepository)
        }
        val customer = command.toDomain()

        customerRepository.save(customer)

        return RegisterCustomerResult(
            id = customer.id.value,
            fullName = customer.fullName,
            cpf = customer.cpf.value,
        )
    }
}
