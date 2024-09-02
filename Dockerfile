FROM alpine:3.20

# Set working directory
WORKDIR /app

# Install necessary packages
RUN apk update && apk upgrade
RUN apk add --no-cache openjdk17-jdk bash curl

# Copy application files
COPY . /app/Streaming

# Build the application using Gradle
RUN cd Streaming && ./gradlew build

# Run the application
ENTRYPOINT ["java", "-jar", "/app/Streaming/build/libs/Streaming-0.0.1-SNAPSHOT.jar"]

# Expose port
EXPOSE 8080