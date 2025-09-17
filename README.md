## Loan App Backend

A Kotlin Spring Boot backend for a loan application system. This is a study project and an example implementation of Domain-Driven Design (DDD). It currently provides identity features such as user registration, with H2 in-memory database, Spring Security, and JPA.

### Tech Stack
- **Language**: Kotlin (JVM)
- **Framework**: Spring Boot 3
- **Persistence**: Spring Data JPA, H2 (in-memory)
- **Security**: Spring Security (BCrypt)
- **Build**: Gradle (Wrapper included)
- **Testing**: JUnit 5, Spring Boot Test, Spring Security Test
- **Coverage**: JaCoCo

### Project Structure
```
src/
  main/
    kotlin/com/loanapp/
      Application.kt
      identity/
        api/v1/            # REST controllers and DTOs
        app/               # Application services (use cases)
        domain/            # Entities, value objects, repositories
        infra/             # Persistence + security implementations
      shared/              # Cross-cutting concerns (e.g., exception handler)
    resources/
      application.yml      # Runtime configuration
  test/
    kotlin/com/loanapp/    # Integration and unit tests
    resources/             # Test configuration
```

### Domain-Driven Design (DDD)
- This project follows a layered DDD structure:
  - **api**: HTTP boundary (controllers, DTOs, mappers)
  - **app**: application services (use cases, orchestrating domain logic)
  - **domain**: entities, value objects, repository interfaces, domain exceptions
  - **infra**: persistence and security implementations, adapters for external tech
-
The goal is clarity of boundaries, testability, and technology-agnostic domain logic.

### Prerequisites
- Java 21 (or a compatible JDK)
- No need to install Gradle — the project uses the Gradle Wrapper.

Check Java version:
```bash
java -version
```

### Configuration
Default configuration is defined in `src/main/resources/application.yml`:
- H2 in-memory database at `jdbc:h2:mem:loanappdb`
- H2 console enabled at `/h2-console`
- Server runs on port `8080`

Test configuration (`src/test/resources/application-test.yml`) uses an isolated in-memory DB and `create-drop` schema.

### Running the Application
Using the Gradle Wrapper:
```bash
./gradlew bootRun
```

The app will start on `http://localhost:8080`.

H2 Console:
```text
URL:      http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:loanappdb
User:     sa
Password: (leave empty)
```

### REST API
Base path: `/v1/auth`

- Register user
  - Method: `POST`
  - Path: `/v1/auth/register`
  - Request body:
    ```json
    {
      "email": "user@example.com",
      "password": "StrongPassword123!"
    }
    ```
  - Success response (201/200 depending on controller defaults):
    ```json
    {
      "id": "<uuid>",
      "email": "user@example.com"
    }
    ```
  - Error cases:
    - 400/409 when email already exists

Security:
- `/v1/auth/register` and `/v1/auth/login` are permitted to all per `SecurityConfig`.
- All other endpoints require authentication (future features).

### Building
```bash
./gradlew clean build
```
Artifacts are produced under `build/`. To build an executable jar:
```bash
./gradlew bootJar
```

### Tests and Coverage
- Run tests:
  ```bash
  ./gradlew test
  ```
- Generate coverage report (auto-runs after tests):
  - HTML: `build/reports/jacoco/test/html/index.html`
  - XML: `build/reports/jacoco/test/jacocoTestReport.xml`

### Code Style & Conventions
- Kotlin with Spring idioms
- Domain-driven layering: `api` → `app` → `domain` → `infra`
- Value objects for `Email`, `Password`, `UserId`
- Centralized error handling in `shared/api/GlobalExceptionHandler.kt`

### Local Development Tips
- If you modify JPA entities, the default `ddl-auto: update` will evolve the in-memory schema at runtime.
- Use the H2 console to inspect tables (`AUTH_USER` etc.).
- Enable SQL logs via `spring.jpa.show-sql: true` (already enabled by default).

### Packaging and Container Image
Build an OCI image using Spring Boot buildpacks:
```bash
./gradlew bootBuildImage
```
Then run with Docker/Podman, exposing port 8080.

### Troubleshooting
- H2 console not loading: ensure the app is running and visit `/h2-console` (exact path configured).
- Cannot connect to DB: check the JDBC URL matches the configuration.
- Port in use: change `server.port` in `application.yml` or free the port.

### License
Add your license here (e.g., MIT, Apache-2.0).


