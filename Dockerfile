FROM eclipse-temurin:17.0.8_7-jdk AS build
WORKDIR /app

COPY . .
RUN chmod +x ./gradlew

COPY env/prod.env /app/env/prod.env
ENV GRADLE_OPTS="-Xmx1g -Dorg.gradle.daemon=false"
RUN ./gradlew build --no-daemon -x test --no-build-cache

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
