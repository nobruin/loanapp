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
- **Persistence**: Spring Data JPA, H2 (in-memory)
- **Security**: Spring Security (BCrypt)
- **Build**: Gradle (Wrapper included)
- **Testing**: JUnit 5, Spring Boot Test, Spring Security Test
- **Coverage**: JaCoCo

## Configuration

### Application Configuration
Default configuration is defined in `src/main/resources/application.yml`:
- H2 in-memory database at `jdbc:h2:mem:loanappdb`
- H2 console enabled at `/h2-console`
- Server runs on port `8080`

### Test Configuration
Test configuration (`src/test/resources/application-test.yml`) uses an isolated in-memory DB and `create-drop` schema.

## Running the Application

### Development Mode
Using the Gradle Wrapper:
```bash
./gradlew bootRun
```

The app will start on `http://localhost:8080`.

### H2 Database Console
Access the H2 console for database inspection:
```text
URL:      http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:loanappdb
User:     sa
Password: (leave empty)
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

Artifacts are produced under `build/` directory.

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

## Local Development Tips

### Database Management
- If you modify JPA entities, the default `ddl-auto: update` will evolve the in-memory schema at runtime
- Use the H2 console to inspect tables (`AUTH_USER` etc.)
- SQL logs are enabled by default via `spring.jpa.show-sql: true`

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

### Production Considerations
- Replace H2 with a production database (PostgreSQL, MySQL)
- Configure proper security settings
- Set up monitoring and logging
- Use environment-specific configuration files

## Troubleshooting

### Common Issues

**H2 console not loading:**
- Ensure the app is running
- Visit `/h2-console` (exact path configured)
- Check that H2 console is enabled in configuration

**Cannot connect to database:**
- Verify the JDBC URL matches the configuration
- Check that the database driver is available
- Ensure no other application is using the same database

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
- Review Spring Boot documentation
- Consult Kotlin and Spring Security guides
