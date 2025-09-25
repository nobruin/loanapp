package com.loanapp.shared.api

import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun customOpenAPI(): OpenAPI =
        OpenAPI()
            .info(
                Info()
                    .title("Loan App Backend API")
                    .description("Study project demonstrating DDD with Spring Boot and Kotlin")
                    .version("0.0.1")
                    .license(License().name("MIT").url("https://opensource.org/licenses/MIT"))
                    .contact(
                        Contact()
                            .name("bruno.marins nobruin@gmail.com"),
                    ),
            ).externalDocs(
                ExternalDocumentation()
                    .description("Project documentation")
                    .url("https://github.com/your-org/loan-app-backend"),
            )
}
