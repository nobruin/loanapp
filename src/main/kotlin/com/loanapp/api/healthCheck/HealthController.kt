package com.loanapp.api.healthCheck

import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {

    @GetMapping("public/health")
    fun publicCheck(): Map<String, String> =
        mapOf("status" to "UP", "auth" to "none")

    @GetMapping("secure/health")
    fun secureHealth(@AuthenticationPrincipal jwt: Jwt): Map<String, Any?>{
        return mapOf(
            "status" to "UP",
            "sub" to jwt.subject,
            "email" to jwt.claims["email"],
            "iss" to jwt.claims["iss"],
            "aud" to jwt.claims["aud"]
        )
    }
}