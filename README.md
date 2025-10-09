# Loan App Backend

A Kotlin Spring Boot backend for a loan application system. This is a study project and an example of Domain-Driven Design (DDD). It offers identity features like user registration, using **PostgreSQL** as the primary database for development and production, with Spring Security and JPA. **H2** is used only for integration testing.

## Quick Start

### Prerequisites
- Java 21 (or any compatible JDK)
- Python 3 and pip (required for pre-commit hooks)
- Docker and Docker Compose (recommended for local development)
- No need to install Gradle — the Gradle Wrapper is included

### Running with Docker (Recommended)
```bash
docker-compose up --build
```
The API will be available at `http://localhost:8080` and PostgreSQL at `localhost:5432`.

### Running Locally (without Docker)
Make sure a PostgreSQL instance is running locally with the following defaults:
- host: `localhost`
- port: `5432`
- database: `loanapp`
- user: `postgres`
- password: `postgres`

Then start the application:
```bash
./gradlew bootRun
```

### Integration Testing
Integration tests use **H2 in-memory** database automatically. There is no need to configure H2 for development or production.

## Database Configuration
- **Development & Production:** PostgreSQL (see configs in `application-dev.yml` and `application-prod.yml`)
- **Tests:** H2 in-memory (test environments only)

## Pre-commit Hooks
Pre-commit is used to ensure code quality and commit conventions.

To install the hooks:
```bash
bash scripts/setup.sh
```
> ⚠️ **Important:**
> The script *does not* install pip. You must install it manually, for example (on Ubuntu):
> ```bash
> sudo apt install python3 python3-pip -y
> ```

The script will:
- Check for Python 3
- Install pre-commit using pip
- Set up hooks for Kotlin lint (ktlint) and Conventional Commits

To run the hooks manually:
```bash
pre-commit run --all-files
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

### H2 Database Console
**Note:** The H2 console is now only used in integration test environments, not in development or production.

## Current Features

- ✅ User registration with email validation
- ✅ Password encryption using BCrypt
- ✅ PostgreSQL database (H2 only for integration tests)
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