package com.loanapp.api.customer

import com.fasterxml.jackson.databind.ObjectMapper
import com.loanapp.api.customer.dto.RegisterCustomerRequest
import com.loanapp.shared.BaseIntegrationTest
import com.loanapp.support.JwtFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.time.LocalDate

class CustomerControllerIntegrationTest
    @Autowired
    constructor(
        private val mockMvc: MockMvc,
        private val objectMapper: ObjectMapper,
    ) : BaseIntegrationTest() {
        private lateinit var baseRequest: RegisterCustomerRequest

        @BeforeEach
        fun setup() {
            baseRequest =
                RegisterCustomerRequest(
                    fullName = "Jane Doe",
                    cpf = "98765432100",
                    birthDate = LocalDate.of(1995, 5, 10),
                    phone = "+5511999999999",
                    address = "456 Another St",
                )
        }

        private fun performRequest(
            sub: String = "auth0|integrationTestUser",
            body: RegisterCustomerRequest = baseRequest,
            email: String = "jane.doe@example.com",
        ) = mockMvc.post("/v1/customers") {
            with(
                JwtFactory.withJwt(
                    sub = sub,
                    email = email,
                ),
            )
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(body)
        }

        @Test
        fun `should register a new customer successfully`() {
            performRequest().andExpect {
                status { isCreated() }
                jsonPath("$.id") { exists() }
                jsonPath("$.fullName") { value("Jane Doe") }
                jsonPath("$.cpf") { value("98765432100") }
            }
        }

        @Test
        fun `should return 400 when email already exists`() {
            val email = "duplication_email@test.com"
            val duplicateEmailReq =
                baseRequest.copy(
                    cpf = "12345678901",
                )

            performRequest(sub = "auth1|userA", body = duplicateEmailReq, email = email).andExpect {
                status { isCreated() }
            }

            performRequest(
                sub = "auth2|userB",
                email = email,
                body =
                    duplicateEmailReq.copy(
                        cpf = "10987654321",
                    ),
            ).andExpect {
                status { isBadRequest() }
                jsonPath("$.message") {
                    value("Email $email is already in use")
                }
            }
        }

        @Test
        fun `should return 400 when externalUserId already linked`() {
            performRequest(
                sub = "auth0|same-sub",
                baseRequest.copy(
                    cpf = "44455566699",
                ),
                email = "singular@test.com",
            ).andExpect {
                status { isCreated() }
            }

            val duplicateSubReq =
                baseRequest.copy(
                    cpf = "44455566677",
                )

            performRequest(
                sub = "auth0|same-sub",
                body = duplicateSubReq,
                email = "different_email@test.com",
            ).andExpect {
                status { isBadRequest() }
                jsonPath("$.message") {
                    value("User already linked to a customer")
                }
            }
        }

        @Test
        fun `should return 400 when cpf already exists`() {
            val duplicateCpfReq =
                baseRequest.copy(
                    cpf = "11011011102",
                )

            performRequest(sub = "auth3|userD", body = duplicateCpfReq, email = "email@test.com").andExpect {
                status { isCreated() }
            }

            performRequest(
                sub = "auth4|userE",
                body =
                duplicateCpfReq,
                email = "different_email@test.com",
            ).andExpect {
                status { isBadRequest() }
                jsonPath("$.message") {
                    value("CPF ${duplicateCpfReq.cpf} is already in use")
                }
            }
        }
    }
