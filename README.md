# Loan App Backend

A Kotlin Spring Boot backend for a loan application system. This is a study project and an example implementation of Domain-Driven Design (DDD). It currently provides identity features such as user registration, with H2 in-memory database, Spring Security, and JPA.

## Quick Start

### Prerequisites
- Java 21 (or compatible JDK)
- No need to install Gradle — the project uses the Gradle Wrapper

### Running the Application
```bash
./gradlew bootRun
```

The app will start on `http://localhost:8080`.

### H2 Database Console
```text
URL:      http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:loanappdb
User:     sa
Password: (leave empty)
```

## Documentation

### 📖 Swagger UI
Once the app is running, explore and test the API via Swagger UI:
```
http://localhost:8080/swagger-ui.html
```
OpenAPI JSON is available at:
```
http://localhost:8080/v3/api-docs
```

### 📋 [Development Guide](docs/development.md)
Complete setup, building, testing, and deployment instructions.

### 🏗️ [Architecture & DDD](docs/architecture.md)
Domain-Driven Design principles, strategic vision, and project structure.

### 🔌 [API Documentation](docs/api.md)
REST API endpoints, request/response examples, and security details.

## Current Features

- ✅ User registration with email validation
- ✅ Password encryption using BCrypt
- ✅ H2 in-memory database with console access
- ✅ Spring Security configuration
- ✅ Domain-driven architecture
- ✅ Integration tests with test coverage

## Planned Features

- 🔄 User authentication (login)
- 🔄 Loan application submission
- 🔄 Risk assessment and approval workflow
- 🔄 Payment scheduling and tracking
- 🔄 Notification system
- 🔄 Production database integration

## Tech Stack

- **Language**: Kotlin (JVM)
- **Framework**: Spring Boot 3
- **Persistence**: Spring Data JPA, H2 (in-memory)
- **Security**: Spring Security (BCrypt)
- **Build**: Gradle (Wrapper included)
- **Testing**: JUnit 5, Spring Boot Test, Spring Security Test
- **Coverage**: JaCoCo

## License

Add your license here (e.g., MIT, Apache-2.0).