# Build stage
FROM gradle:8.14.0-jdk21 AS builder

WORKDIR /build

COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradle gradle
COPY src src

RUN gradle build -x test

# Runtime stage
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /build/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]