package com.loanapp.infra.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .authorizeHttpRequests { auth ->
                auth
                    // public endpoints
                    .requestMatchers("/public/**")
                    .permitAll()
                    .requestMatchers("/h2-console/**")
                    .permitAll() // to h2 in DEV
                    .requestMatchers("/actuator/**")
                    .permitAll() // Health checks
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**")
                    .permitAll() // Swagger
                    .anyRequest()
                    .authenticated()
            }.oauth2ResourceServer { oauth2 ->
                oauth2.jwt { }
            }.csrf { it.disable() }
            .headers { headers ->
                headers.frameOptions { it.disable() }
            }.build()
}
