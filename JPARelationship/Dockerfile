# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/JPARelationship.jar app.jar

# Expose the port your application will run on
EXPOSE 9000

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]