FROM ubuntu:latest

WORKDIR /app

RUN apt update && apt upgrade -y
RUN apt install wget -y
RUN apt install openjdk-17-jdk openjdk-17-jre -y

COPY . /app/Streaming

RUN cd Streaming && ./gradlew build

ENTRYPOINT ["java", "-jar", "/app/Streaming/build/libs/Streaming-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080
