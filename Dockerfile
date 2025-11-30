#FROM registry.access.redhat.com/ubi8/openjdk-17:latest
#ADD target/file-upload-service-0.0.1-SNAPSHOT.jar file-upload-service-0.0.1-SNAPSHOT.jar
#ENTRYPOINT ["sh", "-c", "java -jar /file-upload-service-0.0.1-SNAPSHOT.jar"]

# Build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /file-upload-service
COPY pom.xml .
COPY src ./src
RUN mvn -DskipTests package

# Runtime stage
FROM registry.access.redhat.com/ubi8/openjdk-17:latest
#FROM eclipse-temurin:17-jre
WORKDIR /file-upload-service
COPY --from=build /file-upload-service/target/*.jar file-upload-service-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","file-upload-service-0.0.1-SNAPSHOT.jar"]

