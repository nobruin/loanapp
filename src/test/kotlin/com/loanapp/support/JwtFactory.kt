package com.loanapp.support

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt
import org.springframework.test.web.servlet.request.RequestPostProcessor

object JwtFactory {
    /**
     * Creates a mocked RequestPostProcessor, simulating a token issued by Auth0.
     *
     * @param sub claim value "sub" (ex: "auth0|12345")
     * @param email email value of user
     */
    fun withJwt(
        sub: String = "auth0|test-user",
        email: String = "test@loanapp.com",
    ): RequestPostProcessor =
        jwt().jwt { jwt ->
            jwt.claim("sub", sub)
            jwt.claim("email", email)
            jwt.claim("iss", "https://dev-7v8qmxq2rcmv3w7w.us.auth0.com/")
            jwt.claim("aud", listOf("https://loan-app/api"))
        }
}
