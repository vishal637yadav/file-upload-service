FROM registry.access.redhat.com/ubi8/openjdk-17:latest
ADD ../target/file-upload-service-0.0.1-SNAPSHOT.jar file-upload-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["sh", "-c", "java -jar /file-upload-service-0.0.1-SNAPSHOT.jar"]
