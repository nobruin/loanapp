package com.loanapp.identity.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.loanapp.identity.api.v1.dto.LoginRequest
import com.loanapp.identity.domain.AuthUser
import com.loanapp.identity.domain.AuthUserRepository
import com.loanapp.identity.domain.Email
import com.loanapp.identity.domain.Password
import com.loanapp.identity.domain.UserId
import com.loanapp.identity.infra.persistence.AuthUserJpaRepository
import com.loanapp.shared.BaseIntegrationTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class LoginUserIntegrationTest(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
    val authUserRepository: AuthUserRepository,
    val jpaRepository: AuthUserJpaRepository,
    val encoder: PasswordEncoder
): BaseIntegrationTest() {

    @BeforeEach
    fun setup(){
        jpaRepository.deleteAll()
        val user = AuthUser(
            id = UserId.new(),
            email = Email(VALID_EMAIL),
            password = Password.fromPlainText(VALID_PASSWORD, encoder)
        )
        authUserRepository.save(user)
    }

    @Test
    fun `should login successfully with correct credentials`() {
        val request = LoginRequest(VALID_EMAIL,VALID_PASSWORD)

        mockMvc.post(LOGIN_URL) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }
            .andExpect {
                status { isOk() }
                jsonPath("$.userId") { exists() }
                jsonPath("$.token") { value("jwt-token") }
            }
    }

    @Test
    fun `should return error for invalid email`() {
        val request = LoginRequest(
            email = "invalid-email",
            password = VALID_PASSWORD
        )

        mockMvc.post(LOGIN_URL) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isBadRequest() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }
    }

    @Test
    fun `should return error for invalid password`() {
        val request = LoginRequest(
            email = VALID_EMAIL,
            password = "invalid_password"
        )

        mockMvc.post(LOGIN_URL) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isBadRequest() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }
    }

    companion object {
        const val LOGIN_URL = "/v1/auth/login"
        const val VALID_EMAIL = "login@example.com"
        const val VALID_PASSWORD = "12345@AB"
    }

}