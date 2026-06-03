# Use Eclipse Temurin 21 JDK (production-grade, reliable)
FROM eclipse-temurin:21-jdk-jammy

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file from Maven build
# target/module3-1.0-SNAPSHOT.jar gets renamed to app.jar for simplicity
COPY target/module3-1.0-SNAPSHOT.jar app.jar

# Expose port 8080 (Spring Boot default port)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]