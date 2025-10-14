package com.loanapp.application.customer

import com.loanapp.application.customer.dto.RegisterCustomerCommand
import com.loanapp.domain.customer.CustomerRepository
import com.loanapp.domain.shared.valueobject.Email
import org.springframework.stereotype.Component

@Component
class EmailInUseValidation : CustomerRegisterValidation {
    override fun perform(
        command: RegisterCustomerCommand,
        repository: CustomerRepository,
    ) {
        require(!repository.isEmailInUse(Email(command.email))) {
            "Email ${command.email} is already in use"
        }
    }
}
