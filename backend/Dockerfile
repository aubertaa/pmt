FROM openjdk:17-jdk-alpine

# Set the build argument
ARG SENDGRID_API_KEY

# Set the environment variable in the container
ENV SENDGRID_API_KEY=${SENDGRID_API_KEY}

# Create a directory for the app
WORKDIR /app

# Copy the JAR file from the GitHub Action's workspace into the image
COPY "build/libs/pmtbackend-0.0.1-SNAPSHOT.jar" "/app/pmtbackend-0.0.1-SNAPSHOT.jar"

# Run the JAR file
CMD ["java", "-jar", "/app/pmtbackend-0.0.1-SNAPSHOT.jar"]
