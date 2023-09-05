# Use a base image with Java pre-installed
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file built from your Spring Boot application to the container
COPY target/your-spring-boot-app.jar app.jar

# Expose the port your Spring Boot application will run on (adjust if needed)
EXPOSE 8080

# Define an entry point to run your application when the container starts
CMD ["java", "-jar", "app.jar"]

