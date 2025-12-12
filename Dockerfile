FROM eclipse-temurin:17-jdk AS builder

WORKDIR /workspace/app

# Copy Maven files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Copy source and build
COPY src src
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-jammy

# Create non-root user
RUN addgroup --system appuser && adduser --system --ingroup appuser appuser
USER appuser

WORKDIR /app

# Copy the built JAR file
COPY --from=builder --chown=appuser:appuser /workspace/app/target/*.jar app.jar

# Environment variables
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom \
               -XX:+UseContainerSupport \
               -XX:MaxRAMPercentage=75.0"

EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run the fat JAR (Spring Boot handles classpath automatically)
ENTRYPOINT ["java", "-jar", "app.jar"]
