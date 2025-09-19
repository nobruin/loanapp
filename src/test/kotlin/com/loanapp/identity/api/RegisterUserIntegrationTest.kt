package com.loanapp.identity.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.loanapp.identity.api.v1.dto.RegisterUserRequest
import com.loanapp.identity.domain.AuthUserRepository
import com.loanapp.identity.domain.Email
import com.loanapp.shared.BaseIntegrationTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RegisterUserIntegrationTest
    @Autowired
    constructor(
        val mockMvc: MockMvc,
        val objectMapper: ObjectMapper,
        val authUserRepository: AuthUserRepository,
        val encoder: PasswordEncoder,
    ) : BaseIntegrationTest() {
        @Test
        fun `should register a  new user`() {
            val emailToSave = "user@example.com"
            val request =
                RegisterUserRequest(
                    email = emailToSave,
                    password = VALID_PASSWORD,
                )

            val mvcResult =
                mockMvc
                    .post(REGISTER_URL) {
                        contentType = MediaType.APPLICATION_JSON
                        content = objectMapper.writeValueAsString(request)
                    }.andExpect {
                        status { isOk() }
                        content { contentType(MediaType.APPLICATION_JSON) }
                    }.andReturn()

            val response = objectMapper.readTree(mvcResult.response.contentAsString)
            assertEquals(emailToSave, response.get("email").asText())
            assertNotNull(response.get("id").asText())

            val savedUser = authUserRepository.findByEmail(Email(emailToSave))
            assertNotNull(savedUser)
            assertEquals(emailToSave, savedUser.email.value)
            assertTrue(savedUser.verifyPassword(VALID_PASSWORD, encoder))
        }

        @Test
        fun `should return error for invalid email`() {
            val request =
                RegisterUserRequest(
                    email = "invalid-email",
                    password = VALID_PASSWORD,
                )

            mockMvc
                .post(REGISTER_URL) {
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(request)
                }.andExpect {
                    status { isBadRequest() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                }
        }

        @Test
        fun `should return error for short password`() {
            val request =
                RegisterUserRequest(
                    email = "valid_email@example.com",
                    password = "123",
                )

            mockMvc
                .post(REGISTER_URL) {
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(request)
                }.andExpect {
                    status { isBadRequest() }
                }
        }

        @Test
        fun `should return conflict when email already exists`() {
            val email = "duplicate@example.com"

            val firstRequest =
                RegisterUserRequest(
                    email = email,
                    password = "12345@AB",
                )

            mockMvc
                .post(REGISTER_URL) {
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(firstRequest)
                }.andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                }.andReturn()

            val secondRequest =
                RegisterUserRequest(
                    email = email,
                    password = "12345@AB",
                )

            mockMvc
                .post(REGISTER_URL) {
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(secondRequest)
                }.andExpect {
                    status { isConflict() }
                }
        }

        companion object {
            const val REGISTER_URL = "/v1/auth/register"
            const val VALID_PASSWORD = "12345@AB"
        }
    }
