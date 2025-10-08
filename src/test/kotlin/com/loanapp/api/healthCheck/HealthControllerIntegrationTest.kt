package com.loanapp.api.healthCheck

import com.loanapp.shared.BaseIntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

class HealthControllerIntegrationTest
    @Autowired
    constructor(
        val mockMvc: MockMvc,
    ) : BaseIntegrationTest() {
        @Test
        fun `should return UP on public health without authentication`() {
            mockMvc
                .get("/public/health")
                .andExpect {
                    status { isOk() }
                    jsonPath("$.status") { value("UP") }
                    jsonPath("$.auth") { value("none") }
                }
        }

        @Test
        fun `should return Unauthorized when calling secure health without token`() {
            mockMvc
                .get("/secure/health")
                .andExpect {
                    status { isUnauthorized() }
                }
        }

        @Test
        fun `should return UP with claims when calling secure health with valid JWT`() {
            mockMvc
                .get("/secure/health") {
                    with(
                        jwt().jwt { jwt ->
                            jwt.claim("sub", "auth0|123456")
                            jwt.claim("email", "user@test.com")
                            jwt.claim("iss", "https://dev-7v8qmxq2rcmv3w7w.us.auth0.com/")
                            jwt.claim("aud", listOf("https://loan-app/api"))
                        },
                    )
                }.andExpect {
                    status { isOk() }
                    jsonPath("$.status") { value("UP") }
                    jsonPath("$.sub") { value("auth0|123456") }
                    jsonPath("$.email") { value("user@test.com") }
                }
        }
    }
