# Development Guide

## Prerequisites
- Java 21 (or a compatible JDK)
- No need to install Gradle — the project uses the Gradle Wrapper.

Check Java version:
```bash
java -version
```

## Tech Stack
- **Language**: Kotlin (JVM)
- **Framework**: Spring Boot 3
- **Persistence**: Spring Data JPA, PostgreSQL for development/production, H2 (in-memory) for integration testing
- **Security**: Spring Security (OAuth2 with Okta)
- **Build**: Gradle (Wrapper included)
- **Testing**: JUnit 5, Spring Boot Test, Spring Security Test
- **Coverage**: JaCoCo

## Configuration

### Application Configuration
Default configuration is defined in `src/main/resources/application.yml`:
- PostgreSQL as the primary database for development and production
- Server runs on port `8080`

### Test Configuration
Test configuration (`src/test/resources/application-test.yml`) uses an in-memory H2 database and `create-drop` schema for isolated integration tests only.

## Running the Application

### Development Mode
Using the Gradle Wrapper:
```bash
./gradlew bootRun
```

The app will start on `http://localhost:8080`.

### Docker Environment (Recommended)
To easily run both the API and database in a local environment, use:
```bash
docker-compose up --build
```

## Building

### Clean Build
```bash
./gradlew clean build
```

### Executable JAR
```bash
./gradlew bootJar
```

Artifacts are produced under the `build/` directory.

## Testing

### Run Tests
```bash
./gradlew test
```

### Test Coverage
Coverage reports are automatically generated after tests:
- **HTML Report**: `build/reports/jacoco/test/html/index.html`
- **XML Report**: `build/reports/jacoco/test/jacocoTestReport.xml`

### Integration Tests
Integration tests are located in `src/test/kotlin/com/loanapp/` and use:
- `@SpringBootTest` for full application context
- `@TestPropertySource` for test-specific configuration
- `BaseIntegrationTest` for common test setup
- An in-memory H2 database configured specifically for tests only

## Local Development Tips

### Database Management
- PostgreSQL is always used for local development unless running tests (which use H2 automatically)
- Database connection configs are in `src/main/resources/application-dev.yml` (development) and `application-prod.yml` (production simulation)
- SQL logs can be enabled via `spring.jpa.show-sql: true`

### Code Quality
- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Write tests for new features
- Maintain DDD layer boundaries

### Debugging
- Enable debug logging by adding `logging.level.com.loanapp=DEBUG` to `application.yml`
- Use IDE debugger with breakpoints
- Check application logs for detailed error information

## Packaging and Deployment

### Container Image
Build an OCI image using Spring Boot buildpacks:
```bash
./gradlew bootBuildImage
```

Then run with Docker/Podman, exposing port 8080:
```bash
docker run -p 8080:8080 loan-app-backend:latest
```

### Cloud Deployment & CI/CD
- Cloud simulation/staging is performed via Railway, with deploys triggered by pushing a version git tag (`v*`).
- CI/CD workflows run on every pull request and tag push, building, linting, and testing your code, as well as enforcing commit/PR conventions.
- See the main project README for full details on cloud deployment, tagging, and GitHub Actions.

## Troubleshooting

### Common Issues

**Database connection errors:**
- Verify PostgreSQL is running locally when not using Docker
- JDBC URL must match your environment (see `application-dev.yml`)
- H2 is only used for tests, not development/production

**Port already in use:**
- Change `server.port` in `application.yml`
- Or free the port by stopping other services
- Use `lsof -i :8080` to check port usage

**Build failures:**
- Ensure Java 21 is installed and configured
- Run `./gradlew clean` before building
- Check for dependency conflicts

### Getting Help
- Check application logs for detailed error messages
- Review Spring Boot and Okta OAuth2 documentation
- Consult Kotlin and Spring Security guides
