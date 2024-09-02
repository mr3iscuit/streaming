FROM openjdk:17-alpine

# Set working directory
WORKDIR /app

# Install necessary packages
RUN apk update && apk upgrade
RUN apk add --no-cache bash curl

# Copy application files
COPY ./build/libs/Streaming-0.0.1-SNAPSHOT.jar /app/

# Build the application using Gradle
# RUN cd Streaming && ./gradlew build

# Run the application
ENTRYPOINT ["java", "-jar", "/app/Streaming-0.0.1-SNAPSHOT.jar"]

# Expose port
EXPOSE 8080