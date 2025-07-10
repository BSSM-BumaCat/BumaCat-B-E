FROM eclipse-temurin:17.0.8_7-jdk AS build
WORKDIR /app

COPY . .
RUN chmod +x ./gradlew

ENV GRADLE_OPTS="-Xmx1g -Dorg.gradle.daemon=false"
RUN ./gradlew build --no-daemon -x test --no-build-cache

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY