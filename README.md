# Loan App Backend

A Kotlin Spring Boot backend for a loan application system. This is a study project and an example of Domain-Driven Design (DDD). It offers identity features like user registration, using **PostgreSQL** as the primary database for development and production, with Spring Security and JPA. **Authentication and authorization are integrated with OAuth2 (Okta)**. **H2** is used only for integration testing.

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

- ✅ Customer registration
- ✅ OAuth2 authentication and authorization via Okta
- ✅ PostgreSQL database (H2 only for integration tests)
- ✅ Spring Security configuration
- ✅ Domain-driven architecture
- ✅ Integration tests with test coverage
- ✅ Production-grade cloud deployment simulation via Railway

## Planned Features

- 🔄 Customer registration
- 🔄 Loan application submission
- 🔄 Risk assessment and approval workflow
- 🔄 Payment scheduling and tracking
- 🔄 Notification system

## Tech Stack

- **Language**: Kotlin (JVM)
- **Framework**: Spring Boot 3
- **Persistence**: Spring Data JPA, H2 (in-memory)
- **Security**: Spring Security (OAuth2 with Okta)
- **Build**: Gradle (Wrapper included)
- **Testing**: JUnit 5, Spring Boot Test, Spring Security Test
- **Coverage**: JaCoCo

## Deployment & Cloud Integration

### Production Simulation with Railway
Deployments are automatically triggered to a Railway cloud environment whenever a new git tag matching `v*` (e.g., `v1.0.0`) is pushed. This simulates a production-ready deployment and can be used for staging or demo purposes.

**How it works:**
- Deployment is defined in `.github/workflows/cd-railway.yml`.
- The pipeline installs the Railway CLI, uses organization secrets, and deploys the latest code to the cloud with every tag.
- Railway service and project secrets/tokens must be set in the repository for this to work (see `RAILWAY_TOKEN`, `RAILWAY_PROJECT_ID`, `RAILWAY_SERVICE_ID`).

**To deploy:**
```bash
git tag v1.2.3
git push origin v1.2.3
```

### Continuous Integration
On every pull request:
- The pipeline (`.github/workflows/ci.yml`) builds, lints, and tests your code with JDK 21 and Gradle.
- All commits and PR titles are validated for the [Conventional Commits](https://www.conventionalcommits.org/) format and semantic PR title (`validate-pr-title.yml` / `valide-commits-messages.yml`).
- Runs KtLint, unit/integration tests, and uploads coverage via JaCoCo.
- Optionally, a SonarCloud scan for code quality.

### Commit Message & PR Title Linting
- **Pull request titles** must follow semantic formatting.
- **Commits** must use the Conventional Commits spec. See `.commitlintrc.yml` for configuration.

---
All local and remote builds are validated continuously to ensure production parity and reliable cloud/staging deployments.

## License

Add your license here (e.g., MIT, Apache-2.0).

test sonarcloud integration