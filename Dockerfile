# Use the official, production-ready Eclipse Temurin JDK 17 image
FROM eclipse-temurin:17-jdk

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

# Build the project (Skip BOTH test execution and test compilation)
RUN ./mvnw clean install -Dmaven.test.skip=true

# Run the jar file, dynamically respecting the Port assigned by Render
CMD ["java", "-jar", "target/online.ai-0.0.1-SNAPSHOT.jar"]