# --- Stage 1: Build the Application ---
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
# Execute the build, generating the executable JAR
RUN ./gradlew bootJar

# --- Stage 2: Production Image ---
# Use a lighter base image with only the JRE
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/app.jar app.jar
# Expose the port that Spring Boot uses
EXPOSE 8080
# Define the entrypoint command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
