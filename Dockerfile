# Use official OpenJDK 17 image as base
FROM openjdk:17-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy Maven wrapper and pom.xml first to leverage Docker cache
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copy source code
COPY src ./src

# Make Maven wrapper executable
RUN chmod +x mvnw

# Build the project (skip tests)
RUN ./mvnw clean install -DskipTests

# Run the jar file, dynamically respecting the Port assigned by Render
CMD ["java", "-jar", "target/online.ai-0.0.1-SNAPSHOT.jar"]