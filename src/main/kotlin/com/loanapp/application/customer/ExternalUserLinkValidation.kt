package com.loanapp.application.customer

import com.loanapp.application.customer.dto.RegisterCustomerCommand
import com.loanapp.domain.customer.CustomerRepository
import org.springframework.stereotype.Component

@Component
class ExternalUserLinkValidation : CustomerRegisterValidation {
    override fun perform(
        command: RegisterCustomerCommand,
        repository: CustomerRepository,
    ) {
        require(!repository.existsByExternalUserId(command.externalUserId)) {
            "User already linked to a customer"
        }
    }
}
