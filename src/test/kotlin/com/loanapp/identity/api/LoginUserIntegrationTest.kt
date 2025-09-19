package com.loanapp.identity.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.loanapp.identity.api.v1.dto.LoginRequest
import com.loanapp.identity.api.v1.dto.LoginResponse
import com.loanapp.identity.domain.AuthUser
import com.loanapp.identity.domain.AuthUserRepository
import com.loanapp.identity.domain.Email
import com.loanapp.identity.domain.Password
import com.loanapp.identity.domain.TokenProvider
import com.loanapp.identity.domain.UserId
import com.loanapp.identity.infra.persistence.AuthUserJpaRepository
import com.loanapp.shared.BaseIntegrationTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class LoginUserIntegrationTest(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper,
    val authUserRepository: AuthUserRepository,
    val jpaRepository: AuthUserJpaRepository,
    val encoder: PasswordEncoder,
    val tokenProvider: TokenProvider,
) : BaseIntegrationTest() {

    @BeforeEach
    fun setup() {
        jpaRepository.deleteAll()
        val user =
            AuthUser(
                id = UserId(UUID.fromString(VALID_USER_ID)),
                email = Email(VALID_EMAIL),
                password = Password.fromPlainText(VALID_PASSWORD, encoder),
            )

        authUserRepository.save(user)
    }

    @Test
    fun `should login successfully with correct credentials`() {
        val request = LoginRequest(VALID_EMAIL, VALID_PASSWORD)
        val result =
            mockMvc
                .post(LOGIN_URL) {
                    contentType = MediaType.APPLICATION_JSON
                    content = objectMapper.writeValueAsString(request)
                }.andExpect {
                    status { isOk() }
                    jsonPath("$.userId") { value(VALID_USER_ID) }
                    jsonPath("$.token") { exists() }
                }.andReturn()

        val responseBody = result.response.contentAsString
        val loginResponse = objectMapper.readValue(responseBody, LoginResponse::class.java)
        val extractedUserId = tokenProvider.extractUserId(loginResponse.token)
        
        assertTrue(
            tokenProvider.validateToken(loginResponse.token),
            "The user token must be a valid token",
        )

        assertEquals(
            VALID_USER_ID,
            extractedUserId?.value.toString(),
            "The token's userId must match the logged in user",
        )

        assertEquals(
            VALID_USER_ID,
            loginResponse.userId,
            "" +
                "The userId in the response object should be as expected",
        )
    }

    @Test
    fun `should return error for invalid email`() {
        val request =
            LoginRequest(
                email = "invalid-email",
                password = VALID_PASSWORD,
            )

        mockMvc
            .post(LOGIN_URL) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(request)
            }.andExpect {
                status { isBadRequest() }
                content { contentType(MediaType.APPLICATION_JSON) }
            }
    }

    @Test
    fun `should return error for invalid password`() {
        val request =
            LoginRequest(
                email = VALID_EMAIL,
                password = "invalid_password",
            )

        mockMvc
            .post(LOGIN_URL) {
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
        const val VALID_USER_ID = "98765432-1010-2020-3030-404050607080"
    }
}
