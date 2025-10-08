package com.loanapp.infra

import com.loanapp.domain.customer.Customer
import com.loanapp.domain.shared.valueobject.Cpf
import com.loanapp.domain.shared.valueobject.Email
import com.loanapp.domain.shared.valueobject.Id
import com.loanapp.domain.shared.valueobject.Phone
import com.loanapp.infra.persistencie.customer.CustomerRepositoryImpl
import com.loanapp.shared.BaseIntegrationTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate
import java.util.UUID

class CustomerRepositoryImplTest
    @Autowired
    constructor(
        private val repositoryImpl: CustomerRepositoryImpl,
    ) : BaseIntegrationTest() {
        private fun buildCustomer(cpf: String = "12345678904") =
            Customer(
                id = Id.new(),
                fullName = "John Doe",
                cpf = Cpf(cpf),
                birthDate = LocalDate.of(1995, 5, 20),
                email = Email("john.doe@example.com"),
                phone = Phone("+5511987654321"),
                address = "123 Main St",
            )

        @Test
        fun `should save and find customer`() {
            val customer = buildCustomer()

            val saved = repositoryImpl.save(customer)
            val byId = repositoryImpl.findById(saved.id)
            val byCpf = repositoryImpl.findByCpf(saved.cpf)

            assertNotNull(saved)
            saved.apply {
                assert(this == byId)
                assert(this == byCpf)

                assert(id == customer.id)
                assert(cpf == customer.cpf)
                assert(cpf.value == "12345678904")
            }
        }

        @Test
        fun `should return null when customer not found`() {
            val byId = repositoryImpl.findById(Id.from(UUID.randomUUID()))
            val byCpf = repositoryImpl.findByCpf(Cpf("00000000000"))

            assert(byId == null)
            assert(byCpf == null)
        }

        @Test
        fun `should throw when cpf is duplicated`() {
            val customer = buildCustomer("12345678901")
            repositoryImpl.save(customer)

            assertThrows<org.springframework.dao.DataIntegrityViolationException> {
                repositoryImpl.save(buildCustomer("12345678901"))
            }
        }
    }
