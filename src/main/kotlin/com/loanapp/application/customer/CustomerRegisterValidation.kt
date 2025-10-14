package com.loanapp.application.customer

import com.loanapp.application.customer.dto.RegisterCustomerCommand
import com.loanapp.domain.customer.CustomerRepository

/**
 * Interface for customer record validation strategies.
 */
fun interface CustomerRegisterValidation {
    /**
     * Performs validation.
     * @param command The registration command.
     * @param repository The repository to check for existence in the database.
     * @throws IllegalArgumentException if validation fails.
     */
    fun perform(
        command: RegisterCustomerCommand,
        repository: CustomerRepository,
    )
}
