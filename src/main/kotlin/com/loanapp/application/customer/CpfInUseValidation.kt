package com.loanapp.application.customer

import com.loanapp.application.customer.dto.RegisterCustomerCommand
import com.loanapp.domain.customer.CustomerRepository
import com.loanapp.domain.shared.valueobject.Cpf
import org.springframework.stereotype.Component

@Component
class CpfInUseValidation : CustomerRegisterValidation {
    override fun perform(
        command: RegisterCustomerCommand,
        repository: CustomerRepository,
    ) {
        require(!repository.isCpfInUse(Cpf(command.cpf))) {
            "CPF ${command.cpf} is already in use"
        }
    }
}
